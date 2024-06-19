# Spring Bootキャッチアップのための資料まとめ

## 1. Javaについて学ぶ
* とりあえず[とほほのJava入門](https://www.tohoho-web.com/java/)を一通り目を通す
  * 18~22は読み飛ばしてOK

## 2. Spring Bootセットアップ
### 開発環境構築
* IntelliJ IDEA CE + Gradle + PostgreSQL

### PostgreSQLセットアップ
* docker-composeを使用して、開発用DBを構築する
  * [参考サイト](https://zenn.dev/ayano_sakai/articles/42e64d873bf7df)
  * サイト内ではバージョン14を使用しているが、基本的にlatestを使う
  * PgAdminも併せてセットアップしておくとGUIで確認できて便利

## 3. JPAのインストール
### JPAのインストール
* [参考サイト](https://itsakura.com/sb-jpa-postgresql-select)
  * application.properties
    * 設定値(必須。普段MySQLで検証してたので自信なし)
      ```
      spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/testdb1
      spring.datasource.username=postgres
      spring.datasource.password=postgres
      spring.datasource.driverClassName=org.postgresql.Driver
      ```
    * 設定値（便利機能）
      * SQLログを出してくれたりするプロパティ 
      ```
      spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
      spring.jpa.properties.hibernate.format_sql=true
      logging.level.org.hibernate.SQL=debug
      logging.level.org.hibernate.type.descriptor.sql.BasicBinder=trace
      ```
    * 設定値（環境変数）
      * こんなふうに設定可能。環境変数は`.env`ファイルを作成して、メニュー→「Modify Run Configuration」から環境変数として読み込み可能
      ```
      spring.datasource.url=${DB_URL}
      spring.datasource.username=${DB_USER}
      spring.datasource.password=${DB_PASSWORD}
      spring.datasource.driverClassName=${DB_DRIVER}
      ```
    * application.propertiesを分ける
      * application-test.propertiesみたいなのを作って、テスト時はそちらを読み込む...みたいなことも可能。
      * 開発時は開発用のDBを使って、テスト時はテスト用のDBを使いたい時に便利
      * テストクラスにアノテーションで`@TestPropertySource(locations = "classpath:application-test.properties")`と書けばOK

### Flywayのインストール
* FlywayはDBマイグレーションツール
  * 実行時にDBに対してあらかじめ作成したSQLファイルを実行してくれる
* [インストール参考サイト](https://medium.com/swlh/introduction-of-flyway-with-spring-boot-d7c11145d012)
  * 意外とはまりポイントが多いので、素直にPgAdminからテーブル作成した方がいいかも
* ひとまず`user`でも`book`でもいいので、テーブルを一つ作っておく
  * JPAは基本スネークケースでマッピングを行おうとするので、小文字+スネークケースで作っておくと後々楽

### Repositoryの作成
* PostgreSQLに作成したテーブルに対応するEntityを作成する
* [Spring Data JPA公式のGettingStarted](https://docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html)がわかりやすい

## 4. サービス・コントローラーの実装
### サービス層の実装
* 規約によるが、ひとまずserviceパッケージの直下にインタフェースを、service.implの下に実装クラスを置くと良い
* Entityの他に、サービス層の入出力用にDTOクラスを作成する
  ```java
    @Autowired
    UserRepository repository;
    @Autowired
    UserMapper mapper;
    // 例
    public CreateUserResponse save(CreateUserRequest request) {
        var user = mapper.mapToUser(request);
        return mapper.mapToResponse(repository.save(user));
    }
  ```
* [MapStruct](https://terasolunaorg.github.io/guideline/current/ja/ArchitectureInDetail/GeneralFuncDetail/BeanMapping.html)を入れると、DTO→Entityへの変換を簡単に行ってくれる

### コントローラー層の実装
* RestControllerを作成する
* Swaggerを入れておくと、開発時にデバッグがしやすい
  * [導入方法](https://engineeringnote.hateblo.jp/entry/java/spring/swagger-ui)

## 5.単体テスト
### JUnit
* テスト対象のファイルを開いた状態でCommand(Ctrl)+Shift+Tを押す
  * → 対応するテストファイルが作られる！！！
* [ユーザーガイド](https://junit.org/junit5/docs/current/user-guide/)を読めば感覚掴めると思います
* サービス層→コントローラー層の順にテストを作っていくのがおすすめです

### Mockito
* 呼び方はモキート（諸説あるらしい）
* テスト対象クラスが使用している下位レイヤーのモジュールの動作を模倣するために使います
* また、MvcMockを使ってAPI呼び出しのテストができます

## 6.ロギング
### Slf4j

