# CallLogSystem
flume+kafka+hbase(hadoop)+zookeeper+hive+SSM

运营商通话日志文件是一直都在追加的，CallLog日志会有一个滚动的过程，用flume进行实时收集（Exec类型的Source），收集到之后收集到kafka中，
kafka作为高速缓存，而且kafka是有副本功能，可以容错。
Hbase适合随机定位和实时读写。创建HbaseConsumer从kafka中订阅消息，消息写入Hbase集群。

数据在Hbase中需要避免热点问题，类似于数据倾斜问题，数据集中在其中的一部分节点，造成大量的节点不提供服务，性能很差。
造成热点问题的主要原因就是rowkey，因此需要设计合适的rowkey：使用盐析。第一种是随机盐析，
第二种是hash（本文使用的方式）。

完成的功能：
1.通话详单查询
2.按照时间端查询通话记录
3.用户最近通话信息查询
4.局部实时刷新通话记录
5.指定号码指定年份中各月份的通话次数
