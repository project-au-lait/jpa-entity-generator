# JPA Entity Generator

JPA Entity Generator (jeg) is a tool that reads DB table definitions and generates JPA Entity class java files from them.

## Required Software

The following software is required to use jeg.

- Java 17+
- Maven (when used as the Maven Plugin)

## Usage

You can use jeg as an executable jar and as a Maven Plugin.

### Use as an executable jar

To run jeg as an executable jar, get the jeg jar file from Maven Central and run it with the java command.

```sh
curl -O https://repo1.maven.org/maven2/dev/aulait/jeg/jpa-entity-generator-core/0.11.1/jpa-entity-generator-core-0.11.1-all-deps.jar

java -jar jpa-entity-generator-core-0.11.1-all-deps.jar -c=<configFilePath> -o=<outputDir> --jdbc-url=<jdbcUrl> --jdbc-username=<jdbcUsername> --jdbc-password=<jdbcPassword>
```

See Configuration (#jeg-config) for the specification of the arguments at the end of the above java command.

### Using as a Maven Plugin

To run jeg as the Maven Plugin, add the jpa-entity-generator-maven-plugin setting to pom. xml.

```xml
<plugins>
  <plugin>
    <groupId>dev.aulait.jeg</groupId>
    <artifactId>jpa-entity-generator-maven-plugin</artifactId>
    <version>0.11.1</version>
    <configuration>
      <configFilePath>./jeg-config.yml</configFilePath>
      <jdbcUrl>jdbc:postgresql://localhost:5432/postgres</jdbcUrl>
      <jdbcUsername>postgres</jdbcUsername>
      <jdbcPassword>postgres</jdbcPassword>
      <outputDir>target</outputDir>
    </configuration>
  </plugin>
</plugins>
```

See Configuration (#jeg-config) for configuration settings.
After setting the Plugin in pom. xml, run jeg with the following command:

```sh
mvn jpa-entity-generator:reverse
```


### Settings
<a name="jeg-config"></a>

The java command argument or Maven Plugn configuration item specification for running jeg is as follows:

- configFilePath - Path of the jeg configuration file
- outputDir: The root directory where JPA Entity java files are output.
- metadataOutputDir: The directory where JPA Entity metadata file (`jeg-metadata.json`) is output.
- jdbcUrl: JDBC connection string to the DB for which the JPA Entity is to be generated
- jdbcUsername: User name used to authenticate the DB connection
- jdbcPassword-Password used to authenticate the DB connection

The jeg configuration file configures the generation behavior of the JPA Entity, including which tables to generate and which packages to output to.

How and where the jeg configuration file is specified is determined in the following order (lower number first):

1.  The java command argument or the file specified in the Maven Plugin configuration.
2.  ` jeg-config. yml ` directly under the directory where you want to run the java or Maven command
3.  java command, or ` jeg-config. yml ` under the classpath of the Maven command

The jeg configuration file is created in YAML format.
The specifications of the setting items are as follows.

```yml
runtime:
  # JDBC connection string to the target DB for generating JPA Entity
  jdbcUrl: jdbc:postgresql://localhost:5432/postgres
  # Username used to authenticate DB connection
  jdbcUsername: postgres
  # Password used to authenticate DB connection
  jdbcPassword: postgres
  # Root directory to which JPA Entity java files are output
  outputDir: target
  # Directory to which JPA Entity metadata file is output
  metadataOutputDir: target
# Packages of JPA Entity
packages:
  dev.aulait.jeg.core.domain:
    # Name of the table from which the JPA Entity to be generated under the package
    - TABLE_1
    - TABLE_2
# Definition of base classes extended by generated entities
baseClassDefs:
  - tables:
      - TABLE_1
      - TABLE_2
    baseClass: dev.aulait.jeg.core.domain.CompositeBaseEntity
  - tables:
      - "*"
    baseClass: dev.aulait.jeg.core.domain.BaseEntity
# Definition of Annotaion
annotationDefs:
  uuid:
    type: jakarta.persistence.GeneratedValue
    attributes:
      strategy: jakarta.persistence.GenerationType.UUID
# Columns for entity fields declaring the annotation defined in annotationDefs
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

If you want to apply [google-java-format](https://github.com/google/google-java-format) to the JPA Entity java source file you want to generate, run jpa-entity-generator with the following command:

- When executed with the java command

```sh
java --add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED -jar jpa-entity-generator-core-0.11.1-all-deps.jar 
```

- When running with the Maven Plugin

```sh
MAVEN_OPTS=--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED
```


Source: https://github.com/google/google-java-format?tab=readme-ov-file#as-a-library