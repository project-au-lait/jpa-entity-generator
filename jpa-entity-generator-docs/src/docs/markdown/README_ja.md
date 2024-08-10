# JPA Reverse Engineering Tool

jpa-entity-generator は、DB のテーブル定義を読み取り、それをもとに JPA の Entity クラスの java ファイルを生成するツールです。

## 利用者ガイド

以下のコマンドを実行し、jpa-entity-generator の実行可能な jar を生成します。

```sh
git clone https://github.com/project-au-lait/jpa-entity-generator.git
cd jpa-entity-generator

./mvnw -f jpa-entity-generator-core clean package -P release
```

以上のコマンドを実行すると、`jpa-entity-generator-core/target`ディレクトリ以下に`jpa-entity-generator-core-0.8-all-deps.jar`が生成されます。

`jpa-entity-generator-core-0.8-all-deps.jar`は以下の java コマンドで実行可能です。

```sh
java -jar jpa-entity-generator-core-0.8-all-deps.jar
```

ここで、java コマンドを実行するディレクトリにはリバースエンジニアリングの挙動を制御する設定ファイル`jeg-config.yml`を配置します。

`jeg-config.yml`の設定内容は以下の通りです。

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

生成するJPA Entityのjavaソースファイルに　[google-java-format](https://github.com/google/google-java-format) を適用する場合は、以下のコマンドでjpa-entity-generatorを実行します。

```sh
java --add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED -jar ../jpa-entity-generator-core-0.8-all-deps.jar 
```

参考: https://github.com/google/google-java-format?tab=readme-ov-file#as-a-library

### jpa-entity-generator-maven-plugin導入手順

プロジェクトルートのpom.xmlにjpa-entity-generator-maven-pluginを追加します。

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

以下のコマンドを実行します。

```sh
./mvnw jpa-entity-generator:reverse
```

google-java-formatを適用する場合は、以下のコマンドで環境変数MAVEN_OPTSにJVM引数をしていします。

```sh
MAVEN_OPTS=--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED
```

## 開発者ガイド

以下のコマンドを実行し、開発環境を構築し全てのテストが構築した環境上でパスすることを確認します。

```sh
git clone https://github.com/project-au-lait/jpa-entity-generator.git
cd jpa-entity-generator

./mvnw -N -D ant.target=setup-db

./mvnw -N -D ant.target=test
```

開発に際しては[アーキテクチャ仕様書](https://project-au-lait.github.io/jpa-entity-generator/)を一読してください。
