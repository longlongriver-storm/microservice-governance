# microservice-governance
## 项目说明
本开源项目是《微服务治理：体系、架构及实践》一书的配套源码工程。
<br>
`声明` ：读者可以随意使用本源码，但由于本工程定位是“示例”，缺少全面的鲁棒性设计及完整测试，因此不建议直接在生产环境中使用。
## 目录说明
microservice-governance                      ----->顶层目录<br>
&nbsp;&nbsp;&nbsp;&nbsp;       |-----database                        ----->数据库目录<br>
&nbsp;&nbsp;&nbsp;&nbsp;       |-----storm-algorithm-demo            ----->普通示例子工程目录（包含DSF算法示例、基于AST的Java源码解析示例等）<br>
&nbsp;&nbsp;&nbsp;&nbsp;       |-----storm-monitor                   ----->本书第7、8、9章的示例，一个完整的线上指标埋点、采集、收集、存储、分析的多工程项目 
## 工程层级说明
将源码下载到本地后，用IDE（笔者采用的是netbeans）打开后，会看到这是一个复合工程，包含了一系列的二级及三级子工程，工程结构如下所示：<br>
`storm-microservice-governance`　　　　　　　　　-->一级父工程<br>
　　　　　　|------`storm-algorithm-demo`　　　　　　　　　-->二级工程，普通jar工程，包含了包含DSF算法示例、基于AST的Java源码解析示例等<br>
　　　　　　|------`storm-monitor`　　　　　　　　　　　　　-->二级父工程，包含了7、8、9章的演示示例<br>
　　　　　　　　　　　　|------`storm-monitor-core`　　　　　　　　　-->三级工程，监控核心JAR包工程，包含了采集端和收集服务端的一些核心能力<br>
　　　　　　　　　　　　|------`storm-monitor-client-demo`　　　　　　-->三级工程，一个基于SpringBoot的Web工程，提供事件挡截演示<br>
　　　　　　　　　　　　|------`storm-monitor-server-db`　　　　　　　-->三级工程，监控收集服务端和监控大盘的一些通用功能JAR工程，主要包含对一些监控表的访问能力<br>
　　　　　　　　　　　　|------`storm-monitor-server`　　　　　　　　-->三级工程，监控收集服务端，一个基于SpringBoot的监听服务工程（注意：不是web工程），负责接收来自各采集端的性能、异常、系统指标，并处理入库<br>
　　　　　　　　　　　　|------`storm-monitor-dashboard`　　　　　　　-->三级工程，监控大盘，一个基于SpringBoot的Web工程，提供各类监控大盘、监控配置、图表查看、监控数据查询等能力。<br>
## 运行步骤说明
待...
