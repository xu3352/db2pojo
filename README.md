# Hello db2pojo!

# 是什么?
这个一个代码生成工具, 通过 数据数(mysql/oracle)表 生成 Java对象实体(POJO:普通Java对象), 提高开发效率的实用工具.

在Java后台开发过程中, 基本上都会与数据库进行交互, 而如果每个数据表都对应了后台的一套增删改查, 那么开发量还是比较大和枯燥的.

# 可以做什么?

最初的设想只是为了把 数据表 映射为 Java实体来提高开发效率; 后来发现, 好多模块其实都是大同小异的增删改查, 那么就可以给当前的框架定制一套模板

比如常用的组合框架:`SSM(Spring + SpringMVC + Mybatis)`
- `Controller` : 模块控制器
- `Service` : 模块接口
- `ServiceImpl` : 模块实现
- `Dao` : 模块数据库接口
- `DaoImpl` : 模块数据库实现
- `Mybatis xml` : 模块对应的 mybatis xml 文件, sql语句就放这里了

因为每个系统都有自己的一套框架, 所以第一次的模板配置会比较麻烦, 不过之后就好了, 也就是一次性的工作.

# 谁适合用?
此工具比较适合 `Java老鸟`，对 `Freemarker` 语法有一定了解最好，另外如果此工具满足不了你的特殊需求时，你也可以考虑进行改造，比如页面其实也是可以做出模版的

# 如何使用?
源码clone到本地后，建议使用IDE打开，推荐：`Intelliji IDEA` 或者 `Eclipse`，打开 `Builder.java` 文件，执行入口就在 `Builder.main()` 方法里了

重要的配置文件：`resources/config.json`，在这里可以配置全局的配置，比如项目名称，作者，数据数连接(目前仅支持Mysql和Oracle，根据url自动识别)，指定自定义的模版目录，模版路径映射表。

如果第一次使用，建议使用Mysql随便连接一个数据库，然后直接跑当前的: `Builder.main()` 方法，代码会生成到 `db2pojo/target` 目录下


# 如何配置config.json

|    项目    |  说明  |  示例  ｜
| --- | --- | --- |
| `project` | 全局变量；可以在 `mappings.dir` 里使用；全部的模板文件(`.ftl`)里都可以使用，以 `${project}` 方式嵌入 | 如：`mars`, `com.domain.other` |
| `alias` | 全局变量；使用作用域同project一样，作为project的补充 | 如：`littlemars` |
| `author` | 全局变量；作者名称 | 如Java注释里加上：`xuyl` |
| `dbConfig.driverClass` | 数据库驱动 | 默认为Mysql的驱动，oracle为:`oracle.jdbc.driver.OracleDriver` |
| `dbConfig.username` | 数据库账号 |  |
| `dbConfig.password` | 数据库密码 |  |
| `dbConfig.dbName` | 数据库名 | 作用域为所有的模版文件，在sql生成时可能会用到 |
| `dbConfig.url` | 数据库连接 |  |
| `ignorePrefix` | 在把表名转换为Java驼峰式命名时忽略的前缀，支持多个，以英文逗号分隔开 | `ignorePrefix` 值如果为：`t_` 时，表名 `t_test_table` 转换为类名时则是：`TestTable`  |
| `tableExcludesRegex` | 去除的表正则表达式 | 匹配的表名都将剔除掉 |
| `tableIncludesRegex` | 包含的表正则表达式 | 只有匹配的表才可以保留下来 |
| `templateDir` | 自定义模板根目录 | `resources/template/ssm_cms/` |
| `mappings.template` | 对应根目录下的模板文件名 | 比如POJO对应的模版：`service.ftl` |
| `mappings.output_path` | 更加直观的表示输出路径(Java源文件全路径) | 比如POJO对应的模版：`${project}/web/${alias}/${class_name}Action.java` 


# 如何自定义自己的模板
首先可以自己定义自己的模板文件，熟悉 `Freemarker` 语法的就轻车熟路了，支持迭代数据，格式化，简单的运算之类的

**已支持使用的Freemarker参数**:
- `project` - 字符串，`config.json`里配置的
- `alias` - 字符串，类似 `project`
- `author` - 字符串，作者名
- `dbName` - 字符串，数据库名，生成sql语句时有时会用到
- `package_path` - Java类的包名(`package`)，每个java类都会有自己的包名，而这个包名就是类所在的文件目录的路径名，其实正好就是 `/` 斜杠换成 `.` 就可以了
- `table_name` - 表名，数据库查下到的
- `table_column` - 表对于的所有字段列表集合（数组）
- `class_name` - 类名，由表名根据规则（*去掉前缀,Java驼峰式写法，下划线删掉，首字母大些*）生成
- `instance` - 类对于的实例名，也就是类名首字母小写
- `sysDate` - 当前系统日期
- `hasDateColumn` - 字段中是否包含有日期格式的字段


# 源码结构图
```java
├── README.md
├── lib
│   ├── freemarker-2.3.15.jar                 // 代码生成
│   ├── gson-2.2.2.jar                        // config.json 配置文件解析
│   ├── mysql-connector-java-5.1.12-bin.jar   // mysql连库
│   └── ojdbc5.jar                            // oracle连库
├── resources
│   ├── config.json          // 核心配置文件
│   └── template             // 自定义模板根目录
│       └── ssm_cms          // 一套自定义模板，路径在config.json里指定 "templateDir" : "resources/template/ssm_cms/"
│           ├── action.ftl
│           ├── dao.ftl
│           ├── dao_impl.ftl
│           ├── mybatis.ftl
│           ├── page.ftl
│           ├── pojo.ftl
│           ├── service.ftl
│           └── service_impl.ftl
└── src
    └── com
        └── xu3352
            ├── builder
            │   └── Builder.java                // 工具的入口，直接执行 main 函数即可
            ├── config                          // 配置文件解析相关的实体类
            │   ├── DbConfig.java
            │   ├── Group.java
            │   ├── SetupConfig.java
            │   └── TemplateMapping.java
            ├── core                            // 核心的生成逻辑处理类
            │   ├── BuildFactory.java
            │   └── Column.java
            ├── jdbc                            // 数据库查下相关的接口和2个实现，主要是查下所有的表和表里的所有字段信息
            │   ├── AbstractDaoSupport.java
            │   ├── MysqlDao.java
            │   └── OracleDao.java
            └── util                            // 相关的工具类
                ├── DateUtil.java
                ├── MyUtils.java
                └── StringUtil.java
```

# DONE LIST
- `config.json` 去掉 groups 节点, 输出路径(和报名)以 `mappings.output_path` 为准
- `config.json` 增加一个 `mappings.output_path` 更加直观的表示输出路径(Java源文件全路径)； 这样就可以取代 除了`mappings.template`之外的其他字段了; 
- `config.json` 增加过滤某些表的功能，比如白名单和黑名单之类的

# TODO LIST

