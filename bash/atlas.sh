#编译文件目录  /Users/niufen/server/apache-atlas

#atlas 安装服务器：root@hadooptestgw01.joymo.tech  Modbox123
#solr 服务器：root@hadooptest01.joymo.tech  Modbox123
#一、Atlas配置Solr
#1、在 atlas-application.properties 中修改如下配置
#Solr cloud mode properties
#atlas.graph.index.search.solr.mode=cloud
#atlas.graph.index.search.solr.zookeeper-url=hadooptest01.joymo.tech:2181,hadooptest02.joymo.tech,hadooptest03.joymo.tech
#atlas.graph.index.search.solr.zookeeper-connect-timeout=60000
#atlas.graph.index.search.solr.zookeeper-session-timeout=60000
#atlas.graph.index.search.solr.wait-searcher=true
#2、将 atlas 的conf/solr目录复制到各 solr server 节点的/root/solr-7.5.0目录下，名重命名为atlas_solr/
#2.1、登录 solr 服务器
ssh root@hadooptest01.joymo.tech Modbox123
#2.2、查看solr 进程归属用户
ps aux | grep solr
cat /etc/passwd
#infra-solr:x:1003:1003::/home/infra-solr:/bin/bash
su infra-solr
scp -r /Users/niufen/server/apache-atlas/conf/solr root@hadooptest01.joymo.tech:/home/infra-solr/atlas_solr
#https://blog.csdn.net/weixin_39082031/article/details/78924909
cp -r ~/atlas_solr /usr/lib/ambari-infra-solr/server/solr/configsets/



#2.3 在 solr server 节点，创建collection
./solr create -c vertex_index -d /home/infra-solr/atlas_solr -shards 1 -replicationFactor 3 -force
./solr create -c edge_index -d /home/infra-solr/atlas_solr -shards 1 -replicationFactor 3 -force
./solr create -c fulltext_index -d /home/infra-solr/atlas_solr -shards 1 -replicationFactor 3 -force
#问题 ERROR: Solr requires authentication for http://localhost:8886/solr/admin/info/system. Please supply valid credentials. HTTP code=401
/usr/lib/ambari-infra-solr/bin

sh /usr/hdp/current/zookeeper-client/bin/zkCli.sh -server hadooptest01.joymo.tech:2181,hadooptest02.joymo.tech:2181,hadooptest03.joymo.tech:2181
sh /usr/lib/ambari-infra-solr/server/scripts/cloud-scripts/zkcli.sh -cmd upconfig -confdir ~/atlas_solr -confname SOLR_CONF -z hadooptest01.joymo.tech:2181,hadooptest02.joymo.tech:2181,hadooptest03.joymo.tech:2181/infra-solr

#create collections HTTP ERROR 403
#https://vlambda.com/wz_x0k65qWea5.html
/etc/security/keytabs/ambari-infra-solr.service.keytab
/etc/security/keytabs/infra-solr.keytab

# 从服务器下载文件
scp root@hadooptest01.joymo.tech:/etc/security/keytabs/ambari-infra-solr.service.keytab ~/
scp root@hadooptest01.joymo.tech:/etc/security/keytabs/infra-solr.keytab ~/
scp root@hadooptest01.joymo.tech:/etc/krb5.conf ~/
# 在ambari中查看solr的 http keytab 在本地
kinit -kt ambari-infra-solr.service.keytab HTTP/hadooptest01.joymo.tech@JOYMO.TECH
kinit -kt ambari-infra-solr.service.keytab HTTP/hadooptest01.joymo.tech@JOYMO.TECH
kinit -kt ambari-infra-solr.service.keytab infra-solr/hadooptest01.joymo.tech@JOYMO.TECH
kinit -kt spnego.service.keytab HTTP/hadooptest01.joymo.tech@JOYMO.TECH
kinit -kt infra-solr.keytab infra-solr
#使用 火狐登录 Solr Admin 添加
curl 'http://hadooptest01.joymo.tech:8886/solr/admin/collections?action=RELOAD&name=demo'


#集成hbase
scp -r root@hbasetest01.joymo.tech:/usr/hdp/current/hbase-client/conf /Users/niufen/server/apache-atlas/conf/

-Djava.security.auth.login.config=~/infra_solr_jaas.conf -Dsolr.kerberos.cookie.domain=hadooptest01.joymo.tech -Dsolr.kerberos.cookie.portaware=true -Dsolr.kerberos.principal=HTTP/hadooptest01.joymo.tech@JOYMO.TECH -Dsolr.kerberos.keytab=~/spnego.service.keytab
连接solr相关信息在 http://hadooptest01.joymo.tech:8886/solr/#/~java-properties
java.​security.​auth.​login.​config=/etc/ambari-infra-solr/conf/infra_solr_jaas.conf
scp root@hadooptest01.joymo.tech:/etc/ambari-infra-solr/conf/infra_solr_jaas.conf ~/
