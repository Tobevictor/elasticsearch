**ElasticSearch学习项目**
=====
**简介**
----
        这是一个ElasticSearch的项目。功能包括操作索引，文档和一些常用的查询方法。父项目中暂时有`wuhan-core,wuhan-elasticsearch,wuhan-rabbit,wuhan-redis四个子项目。
-----
wuhan-core:
-----
    目录结构描述
    ├── com.learn                  
    │   ├── elasticsearch
    │   │   ├── aggregate           聚合
    │   │   ├── model               查询对象 
    │   │   ├── query               查询工具
    │   │   │   ├── condition       查询条件
    │   │   │   ├── model           返回对象
    │   │   │   ├── query_enum      枚举
    │   │   ├── suggest             推荐词                           
    │   ├── util                    数据转换工具
    ├── resource
    │   ├── setting                 配置文件                 
    └──
    
    V1.0.0 版本更新
    1. 更新时间   2019.11.25
wuhan-elasticsearch:
-----
    目录结构描述
    ├── logs                        操作日志
    ├── com.learn                  
    │   ├── common                  公共类
    │   │   ├── component           组件
    │   │   ├── config              配置
    │   │   ├── controller          控制器
    │   │   │   ├── mbg             数据操作映射
    │   │   │   ├── model           数据对象    
    │   │   │   ├── service         基本服务         
    ├── resource                                     
    │   ├── setting                 配置文件                   
    └──
    
    V1.0.0 版本更新
    1. 更新时间   2019.11.25
wuhan-rabbitmq:
-----
    目录结构描述
    ├── com.learn                  
    │   ├── component               公共类 
    │   │   ├── config              配置
    │   │   ├── controller          控制器
    │   │   ├── dto                 数据传输对象
    │   │   │   ├── mapper          数据操作映射
    │   │   │   ├── model           数据对象
    │   │   │   ├── service         基本服务
    │   │   ├── suggest                                 
    ├── resource                      
    └──
    
    V1.0.0 版本更新
    1. 更新时间   2019.11.25
wuhan-redis:
-----      
    暂无      
    