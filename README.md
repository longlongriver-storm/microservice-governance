# 《微服务治理：体系、架构及实践》
## 项目说明
本开源项目是《微服务治理：体系、架构及实践》一书的配套源码工程。
<br>
`声明` ：读者可以随意使用本源码，但由于本工程定位是“示例”，缺少全面的鲁棒性设计及完整测试，因此不建议直接在生产环境中使用。
## 目录说明
**`microservice-governance`**                      ----->顶层目录<br>
&nbsp;&nbsp;&nbsp;&nbsp;       |-----**`database`**                        ----->*数据库目录***<br>
&nbsp;&nbsp;&nbsp;&nbsp;       |-----**`storm-algorithm-demo`**            ----->*普通示例子工程目录（包含DSF算法示例、基于AST的Java源码解析示例等）*<br>
&nbsp;&nbsp;&nbsp;&nbsp;       |-----**`storm-monitor`**                   ----->*本书第7、8、9章的示例，一个完整的线上指标埋点、采集、收集、存储、分析的多工程项目* 
## 工程层级说明
将源码下载到本地后，用IDE（笔者采用的是netbeans）打开后，会看到这是一个复合工程，包含了一系列的二级及三级子工程，工程结构如下所示：<br>
**`storm-microservice-governance`**　　　　　　　　　-->*一级父工程*<br>
　　　　　　|------**`storm-algorithm-demo`**　　　　　　　　　-->*二级工程，普通jar工程，包含了包含DSF算法示例、基于AST的Java源码解析示例等*<br>
　　　　　　|------**`storm-monitor`**　　　　　　　　　　　　　-->*二级父工程，包含了7、8、9章的演示示例*<br>
　　　　　　　　　　　　|------**`storm-monitor-core`**　　　　　　　　　-->*三级工程，监控核心JAR包工程，包含了采集端和收集服务端的一些核心能力*<br>
　　　　　　　　　　　　|------**`storm-monitor-client-demo`**　　　　　　-->*三级工程，一个基于SpringBoot的Web工程，提供事件挡截演示*<br>
　　　　　　　　　　　　|------**`storm-monitor-server-db`**　　　　　　　-->*三级工程，监控收集服务端和监控大盘的一些通用功能JAR工程，主要包含对一些监控表的访问能力*<br>
　　　　　　　　　　　　|------**`storm-monitor-server`**　　　　　　　　-->*三级工程，监控收集服务端，一个基于SpringBoot的监听服务工程（注意：不是web工程），负责接收来自各采集端的性能、异常、系统指标，并处理入库*<br>
　　　　　　　　　　　　|------**`storm-monitor-dashboard`**　　　　　　　-->*三级工程，监控大盘，一个基于SpringBoot的Web工程，提供各类监控大盘、监控配置、图表查看、监控数据查询等能力。*<br>
## 运行步骤说明
### 步骤一：初始化数据库
   1、创建监控数据库及各指标存储表：运行database/monitor_create_db_and_table.sql脚本，会创建数据库 `storm_monitor` 及13张指标监控表和一张演示表<br>
   2、初始化自定义数据采集配置和自定义报表配置：运行database/monitor_config_data.sql.sql脚本，会在表`apm_business_cfg`及`apm_chart_confg`中插入一些配置数据
   **请注意，以上两个SQL文件均是UTF-8编码，请在导入时设置好编码格式，以防止出现乱码**
### 步骤二：修改工程中的数据库连接
分别修改如下3项数据库连接：<br>
1、指标收集服务工程的数据库连接，位置：`storm-monitor\server\src\main\resources\jdbc.properties` <br>
2、监控大盘服务工程的数据库连接，位置：`storm-monitor\dashboard\src\main\resources\jdbc.properties`<br>
3、演示客户端工程的数据库连接，位置：`client-demo\src\main\resources\jdbc.properties`<br>
以上3个文件，主要确认`druid.url`，`druid.username`，`druid.password`这三项属性，以保证数据库连接无误。<br>
### 步骤三：编译工程
保证各个工程能够顺利通过Maven编译，并生成相应jar包
### 步骤四：启动指标收集服务
有两种启动方式：<br>
1、开发模式下，运行指标收集服务工程`storm-monitor-server`的“`StormMonitorServerApplication`”类 <br>
2、以"`java -jar storm-monitor-server-1.0.0.jar`"的方式直接运行编译后的SpringBoot部署jar包<br>
启动后，可以通过浏览器访问http://localhost:8092/apmtest/getinfo, 如果页面能够顺利访问，说明服务启动正常。<br>
可以通过修改工程中的`src\main\resources\META-INF\springxml\service-config.xml`中的id为“`apmCommonConfig`”的Bean的`ServerHttpPort`属性来修改访问端口<br>
**相关功能及设计细节请参考《微服务治理：体系、架构及实践》中的第8章**
### 步骤五：启动监控大盘服务
有两种启动方式：<br>
1、开发模式下，运行指标收集服务工程`storm-monitor-dashboard`的“`StormMonitorDashboardApplication`”类 <br>
2、以"`java -jar storm-monitor-dashboard-1.0.0.jar`"的方式直接运行编译后的SpringBoot部署jar包<br>
启动后，可以通过浏览器访问http://localhost:9090/, 如果页面能够顺利访问，说明服务启动正常。<br>
可以通过修改工程中的`src\main\resources\application.yml`中的`server.port`属性来修改web访问端口<br>
### 步骤六：启动客户端演示应用
这个演示应用是一个web应用，有两种启动方式：<br>
1、开发模式下，运行指标收集服务工程`storm-monitor-demo`的“`StormMonitorClientDemoApplication`”类 <br>
2、以"`java -jar storm-monitor-client-demo-1.0.0.jar`"的方式直接运行编译后的SpringBoot部署jar包<br>
启动后，可以通过浏览器访问http://localhost:8080/, 如果页面能够顺利访问，说明服务启动正常。<br>
可以通过修改工程中的`src\main\resources\application.yml`中的`server.port`属性来修改web访问端口<br>
### 步骤七：数据采集
登陆演示客户端：http://localhost:8080/<br>
点击菜单“商品订单（事件样例）”，在这个模拟的订单功能模块中，进行一些增删改查动作，以产生一些事件，便于抓取器进行指标采集和抓取<br>
或者可以点击菜单“调用事件生成器”，点击启动按钮，这个页面会定时调用后台相关功能，可以持续的输出一些模拟事件。<br>
**相关指标采集功能及设计细节请参考《微服务治理：体系、架构及实践》中的第7章**
### 步骤七：指标大盘监控
登陆监控大盘：http://localhost:9090 ，通过各个功能菜单查看各类指标及指标的聚合分析能力。<br>
**相关功能及设计细节请参考《微服务治理：体系、架构及实践》中的第9章**<br>
# 祝各位顺利


