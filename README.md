# JPA Reverse Engineering Tool

Jpa-entity-generator is a tool that reads DB table definitions and generates JPA Entity class java files from them.

## User's Guide

Run the following command to generate an executable jar for the jpa-entity-generator:

```sh
git clone https://github.com/project-au-lait/jpa-entity-generator.git
cd jpa-entity-generator

./mvnw -f jpa-entity-generator-core clean package -P release
```

The above command will generate ` jpa-entity-generator-core-0.8-all-deps.jar ` under the ` jpa-entity-generator-core/target ` directory.

` jpa-entity-generator-core-0.8-all-deps.jar ` can be run with the following java command:

```sh
java -jar jpa-entity-generator-core-0.8-all-deps.jar
```

Now, place a configuration file ` jeg-config.yml ` in the directory where you want to run the java command to control the reverse engineering behavior.

The configuration of ` jeg-config.yml ` is as follows:

```yml
# JDBC connection string to the target DB for generating JPA Entity
jdbcUrl: jdbc:postgresql://localhost:5438/postgres
# Username used to authenticate DB connection
jdbcUsername: postgres
# Password used to authenticate DB connection
jdbcPassword: postgres
# Root directory to which JPA Entity java files are output
outputDir: target
# Packages of JPA Entity
packages:
  dev.aulait.jeg.core.domain:
    # Name of the table from which the JPA Entity to be generated under the package
    - TABLE_1
    - TABLE_2
baseClass: dev.aulait.jeg.core.domain.BaseEntity
# Definition of Annotaion
annotationDefs:
  uuid:
    type: jakarta.persistence.GeneratedValue
    attributes:
      strategy: jakarta.persistence.GenerationType.UUID
# Columns declaring the annotation defined in annotationDefs
annotatedCols:
  main.id:
    - uuid
# Table names not generated JPA Entity
excludedTables:
  - flyway_schema_history
# Column names that will not be generated fields in JPA Entity
excludedColmuns:
  - version
  - created_by
  - created_date
  - updated_by
  - updated_date
# Formatter to be applied to JPA Entity java source files
# If you use google formatter,
# you need to specify JVM options when running jpa-entity-generator (described below)
formatter: google
```

If you want to apply [google-java-format](https://github.com/google/google-java-format) to the java source files of the JPA Entity you want to generate, run jpa-entity-generator with the following command:

```sh
java --add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED -jar ../jpa-entity-generator-core-0.8-all-deps.jar 
```

Reference: https://github.com/google/google-java-format?tab=readme-ov-file#as-a-library

### jpa-entity-generator-maven-plugin Deployment Instructions

Add jpa-entity-generator-maven-plugin to pom. xml in the project root.

pom.xml

```xml
<plugins>
  <plugin>
    <groupId>dev.aulait.jeg</groupId>
    <artifactId>jpa-entity-generator-maven-plugin</artifactId>
    <version>0.8</version>
  </plugin>
</plugins>
```

Run the following command:

```sh
./mvnw jpa-entity-generator:reverse
```

To apply google-java-format, use the JVM argument to the environment variable MAVEN _ OPTS in the following command:

```sh
MAVEN_OPTS=--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED
```

## Developer's Guide

Run the following command to build the development environment and verify that all tests pass on the build environment:

```sh
git clone https://github.com/project-au-lait/jpa-entity-generator.git
cd jpa-entity-generator

./mvnw -f jpa-entity-generator-db -P setup-db

./mvnw -f jpa-entity-generator-db -P migrate-db

./mvnw -N -D ant.target=test
```

Read the [Architecture Specification](https://project-au-lait.github.io/jpa-entity-generator/).