# redis-client-core
redis的操作，自己可以无限扩展 ，方法命名来源于redis api 文档
如果要写可以 对着http://doc.redisfans.com/index.html  直接扩展需要的方法。
比较简单。

基于spring-data-redis
依赖redisTemplate  所以不管你的redis 是3.x的cluster  还是2.8 的sentinel都没有关系
当然也可以直接利用redisTemplate  也很方便。此例子是为了熟悉 spring-data-redis 源码使用
