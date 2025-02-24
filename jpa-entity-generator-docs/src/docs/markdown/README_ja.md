# JPA Entity Generator

JPA Entity Generator (jeg) は、DB のテーブル定義を読み取り、それをもとに JPA の Entity クラスの java ファイルを生成するツールです。

## 必要なソフトウェア

jegを使用するには以下のソフトウェアが必要です。

- Java 17+
- Maven (Maven Pluginとして使用する場合)

## 使用方法

jegは実行可能jar、及びMaven Pluginとして使用できます。

### 実行可能jarとして使用する

jegを実行可能jarとして実行するには、Maven Centralからjegのjarファイルを取得し、javaコマンドで実行します。

```sh
curl -O https://repo1.maven.org/maven2/dev/aulait/jeg/jpa-entity-generator-core/0.10/jpa-entity-generator-core-0.10-all-deps.jar

java -jar jpa-entity-generator-core-0.10-all-deps.jar -c=<configFilePath> -o=<outputDir> --jdbc-url=<jdbcUrl> --jdbc-username=<jdbcUsername> --jdbc-password=<jdbcPassword>
```

上記javaコマンド末尾の引数の仕様は[設定](#jeg-config)を参照してください。

### Maven Pluginとして使用する

jegをMaven Pluginとして実行するには、pom.xmlにjpa-entity-generator-maven-plugin設定を追加します。

```xml
<plugins>
  <plugin>
    <groupId>dev.aulait.jeg</groupId>
    <artifactId>jpa-entity-generator-maven-plugin</artifactId>
    <version>0.10</version>
    <configuration>
      <configFilePath>./jeg-config.yml</configFilePath>
      <jdbcUrl>jdbc:postgresql://localhost:5432/postgres</jdbcUrl>
      <jdbcUsername>postgres</jdbcUsername>
      </jdbcPassword>postgres</jdbcPassword>
      </outputDir>target</outputDir>
    </configuration>
  </plugin>
</plugins>
```

configurationの設定項目は[設定](#jeg-config)を参照してください。
pom.xmlにPluginの設定後、以下のコマンドでjegを実行します。

```sh
mvn jpa-entity-generator:reverse
```


### 設定
<a name="jeg-config"></a>

jegを実行する際のjavaコマンドの引数、またはMaven Plugnのconfiguraion項目の仕様は以下の通りです。

- configFilePath : jeg設定ファイルのパス
- outputDir : JPA Entityのjavaファイルを出力するルートディレクトリ
- metadataOutputDir: JPA Entityのメタデータファイル(`jeg-metadata.json`)を出力するディレクトリ
- jdbcUrl : JPA Entityの生成対象となるDBへのJDBC接続文字列
- jdbcUsername : DB接続の認証に使用するユーザー名
- jdbcPassword : DB接続の認証に使用するパスワード

jeg設定ファイルは、JPA Entityの生成対象テーブルや出力先パッケージなど、生成の挙動を設定します。

jeg設定ファイルの指定方法、配置先は以下の順に決定されます。(番号が小さい方から優先)

1. javaコマンドの引数、またはMaven Pluginのconfigurationで指定されたファイル
2. javaコマンド、またはMavenコマンドを実行するディレクトリ直下の`jeg-config.yml`
3. javaコマンド、またはMavenコマンドのクラスパス以下の`jeg-config.yml`

jeg設定ファイルはYAML形式で作成します。
設定項目の仕様は以下の通りです。

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

生成するJPA Entityのjavaソースファイルに　[google-java-format](https://github.com/google/google-java-format) を適用する場合は、以下のコマンドでjpa-entity-generatorを実行します。

- javaコマンドで実行する場合

```sh
java --add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED -jar jpa-entity-generator-core-0.10-all-deps.jar 
```

- Maven Pluginで実行する場合

```sh
MAVEN_OPTS=--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED
```


参考: https://github.com/google/google-java-format?tab=readme-ov-file#as-a-library
