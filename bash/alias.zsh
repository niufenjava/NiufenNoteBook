
thisPath=$(cd $(dirname ${BASH_SOURCE:-$0});pwd)
echo "别名文件存放路径:" $thisPath
source $thisPath/docker.zsh

alias psall='favoriteps;dubbops;esps;kinabaps;rabbitps;activeps;mongops;redisps;tomcatps;nginxps;mysqlps;zkps'

# jmeter
alias jmeter='jmeter'

# java
alias javaps='ps -ef | grep java | grep -v grep'
alias javakillall='killall java'

# jeecgboot
alias jcbootstart='nohup java -jar /Users/niufen/.m2/repository/org/jeecgframework/boot/jeecg-boot-module-system/2.2.1/jeecg-boot-module-system-2.2.1.jar >> ~/logs/jeecg-boot.log &;jcbootlog'
alias jcbootlog='tail -f ~/logs/jeecg-boot.log -n 2000'
alias jcbootstop='ps -ef | grep jeecg-boot-module-system | grep -v grep | awk '"'"'{print $2}'"'"' | xargs kill -9;jcbootps'
alias jcbootps='ps -ef | grep jeecg-boot-module-system | grep -v grep'
# favorite
alias favoritestart='nohup java -jar ~/server/favorite-api-4.0.0.jar >> ~/logs/favorite.log &;favoritelog'
alias favoritestop='ps -ef | grep favorite-api | grep -v grep | awk '"'"'{print $2}'"'"' | xargs kill -9;favoriteps'
alias favoriteps='ps -ef | grep favorite | grep -v grep'
alias favoritelog='tail -f ~/logs/favorite.log -n 2000'
alias favoriterestart='favoritestop;favoritestart;favoritelog'

# dubboadmin
alias dubbostart='nohup java -jar ~/server/dubbo-admin-server.jar >> ~/logs/dubbo-admin-server.log &;dubbolog'
alias dubbostop='ps -ef | grep dubbo-admin-server | grep -v grep | awk '"'"'{print $2}'"'"' | xargs kill -9;dubbops'
alias dubbops='ps -ef | grep dubbo-admin-server | grep -v grep'
alias dubbolog='tail -f ~/logs/dubbo-admin-server.log -n 200'
alias dubborestart='dubbostop;dubbostart;dubbolog'

# es
alias esstart='brew services start elasticsearch'
alias esstop='brew services stop elasticsearch'
alias esps='ps -ef | grep elasticsearch | grep -v grep'
alias esrestart='esstart;esstop'

# Kinaba
alias kinabastart='brew services start kibana'
alias kinabastop='brew services stop kibana'
alias kinabaps='ps -ef | grep kibana | grep -v grep'
alias kinabarestart='kinabastop;kinabastart'

# RabbitMQ
alias rabbitstart='sudo rabbitmq-server'
alias rabbitstop='sudo ps -ef | grep rabbitmq | grep -v grep | awk '"'"'{print $2}'"'"' | xargs sudo kill -9;rabbitmq'
alias rabbitps='ps -ef | grep rabbitmq | grep -v grep'
alias rabbitrestart='rabbitstop;rabbitstart'

# ActiveMQ
alias activestart='activemq start;activestatus'
alias activestop='activemq stop;activestatus'
alias activestatus='activemq status'
alias activeps='ps -ef | grep activemq | grep -v grep'
alias activerestart='activestop;activestart;'

# MongoDB
alias mongohome='cd ~/server/mongodb/'
alias mongoconf='echo '"'"'~/server/mongodb/master.cfg'"'"''
alias mongoconfcat='cat ~/server/mongodb/master.cfg'
alias mongoconfvim='vim ~/server/mongodb/master.cfg'
alias mongostart='nohup mongod -config ~/server/mongodb/master.cfg > ~/logs/mongodb.log &'
alias mongostop='favoritestop'
alias mongostop='ps -ef | grep mongodb | grep -v grep | awk '"'"'{print $2}'"'"' | xargs kill -9;mongops'
alias mongostatus='mongops'
alias mongops='ps -ef | grep mongodb | grep -v grep'
alias mongorestart='mongostop;mongostart;'
alias mongolog='tail -f ~/logs/mongodb.log -n 200'

# nginx
alias nginxps='ps -ef | grep nginx | grep -v grep'
alias nginxhome='cd /usr/local/Cellar/nginx/1.17.6/'
alias nginxconfcat='cat /usr/local/etc/nginx/nginx.conf'
alias nginxconfvim='vim /usr/local/etc/nginx/nginx.conf'
alias nginxstart='nginx'
alias nginxtest='nginx -t'
alias nginxreload='nginx -s reload'
alias nginxstop='nginx -s stop'
alias nginxrestart='nginxstop;nginxstart'
alias nginxkill='ps -ef | grep nginx | grep -v grep | awk '"'"'{print $2}'"'"' | xargs kill -9'
alias nginxlog='tail -f /Users/niufen/logs/nginx/access.log -n 200'

# redis
alias redisps='ps -ef | grep redis | grep -v grep'
alias redishome='cd ~/server/redis5'
alias rediscli='redis-cli'
alias redisstart='redis-server ~/server/redis5/redis.conf & >> ~/logs/redis6379.log\'
alias redisstop='rediskill'
alias rediskill='ps -ef | grep redis | grep -v grep | awk '"'"'{print $2}'"'"' | xargs kill -9'
alias redisrestart='rediskill;redisstart'

# tomcat
alias tomcatps='ps -ef | grep tomcat | grep -v grep'

# mysql
alias mysqlps='ps -ef | grep mysql | grep -v grep'
alias mysqlhome='cd /usr/local/mysql/'
alias mysqlconfpath='echo '"'"'/etc/my.cnf'"'"''
alias mysqlconfcat='cat /etc/my.cnf'
alias mysqlstart='sudo mysql.server start'
alias mysqlstop='sudo mysql.server stop'
alias mysqlstatus='mysql.server status'

# zookeeper
alias zkps='ps -ef | grep zookeeper | grep -v grep'
alias zkhome='cd /usr/local/Cellar/zookeeper/3.4.10/'
alias zkconfhome='cd /usr/local/etc/zookeeper/'
alias zkconfcat='cat /usr/local/etc/zookeeper/zoo.cfg'
alias zkconfvim='vim /usr/local/etc/zookeeper/zoo.cfg'
alias zkstart='zkServer start;zkstatus'
alias zkcli='zkCli -server localhost:2181'
alias zkstatus='zkServer status'
alias zkstop='zkServer stop'
alias zkrestart='zkstop;zkstart'
alias zkkill='ps -ef | grep zookeeper | grep -v grep | awk '"'"'{print $2}'"'"' | xargs kill -9'
alias zklog='tail -f /usr/local/var/log/zookeeper/zookeeper.log -n 200'

# deerkids
alias deerkidstestssh='ssh root@test.it.deerkids.com'
alias deerkidsmngtestscp='scp /Users/niufen/code/Git-Deerkids/deerKidsMng/deploy/deerKidsMng.war root@test.it.deerkids.com:/root/tomcat9/webapps/'

alias cdhadoop='cd /usr/local/Cellar/hadoop/3.2.1_1'
alias cdhive='cd /usr/local/Cellar/hive/3.1.2_1'
alias cdhbase='/usr/local/Cellar/hbase/1.3.5'
alias cdsqoop='/usr/local/Cellar/sqoop/1.4.7'

alias sb='source ~/.bash_profile'
