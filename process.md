## データベース作成
- データベースに接続(application.propertiesに設定を記載)
- templates内のsqlファイルにテーブル作成のsqlを記載
- 必要なデータがあればinsertも記載してデータを入れる

## データベースのCRUDの準備
- データベースの各テーブルをオブジェクトとして扱う為にEntityアノテーションをつけたクラスで各テーブルのエンティティを宣言する(クラスに@Dataをつけてgetter ,setterは自動生成)
- repositoryインターフェイスも作成し、JpaRepositoryクラスを継承してCRUDを行えるようにする(のちにserviceクラスに継承)

## ログインフォーム作成→ログインまで
- @Configuration,@EnableWebSecurity,@EnableMethodSecurityアノテーションを付けたクラスにてセキュリティの設定を行う
- UserDetailesインターフェイスを実装(implements)したクラスを作成しユーザー情報を定義しているエンティティクラスを注入、またゲッターで取得した値を返すようにメソッドをオーバーライドする。
- セキュリティを設定しているファイル内にて、ログインフォームのURLを指定、ログインできるルートパス、ログイン時、ログイン後のルートパスを指定する。(この際にログイン後のhtmlファイルも記載すれば確認がしやすい。)
- メールアドレスからユーザー名を検索できるようにユーザーエンティティを操作するリポジトリにメソッドを各( 例：public User findByEmail(String email); )
- ログインするユーザー情報のビジネスロックを担当するクラスを作成する（UserDetailServiceインターフェイスを実装）、またユーザー名を取得する為UserRepositoryを継承する
- UserDetailServiceを実装したクラス内で返されたUserDetails型のobjectのnameとpassが入力された値と比較されて、合っていればログインが出来る

## ユーザー登録
- Formクラスを作成する。コントローラーでhtmlファイルにクラスを渡して、フォームに入力をする。→formクラス内でバリデーションを設定する。
- serviceアノテーションを付けたクラスでフォームに入力した値を取得して、そのままクラス内でuserクラスにfromクラスのgetterから取得した値をsetして、リポジトリにsaveする。
```java:spring boot
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(SignupForm signupForm) {
        User user = new User();
        Role role = roleRepository.findByName("ROLE_GENERAL");

        user.setName(signupForm.getName());
        user.setEmail(signupForm.getEmail());
        user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        user.setRole(role);
        user.setEnabled(true);

        return userRepository.save(user);
    }
```

## メールアドレスをトークンで認証
- トークン管理用のテーブルを作成、リポジトリにはfindByToken(String token);を記述してserviceで特定のトークンとリンクしているユーザーを取得、リポジトリを継承してトークンをDBに保存するメソッドを書く。
- サービスクラスでトークンをユーザーごとに発行するメソッドを用意(findBytokenで特定したユーザーにユーザー情報とトークンをセット→保存までのメソッド)
- イベントクラスを用意(AppLicationEventクラスを継承したクラスを用意 ※例signypUserクラス)
- イベントの発生元(publish)のクラスとメソッドの実行(listener)のクラスを作成間にイベントクラスを用意