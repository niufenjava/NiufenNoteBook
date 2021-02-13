
thisPath=$(cd $(dirname ${BASH_SOURCE:-$0});pwd)
echo "别名文件存放路径:" $thisPath
source $thisPath/docker.zsh

alias kinithaiun='kinit -kt ~/haijun.zhang.keytab haijun.zhang'

alias psall='favoriteps;dubbops;esps;kinabaps;rabbitps;activeps;mongops;redisps;tomcatps;nginxps;mysqlps;zkps'
# docker
alias dkps='docker ps'
alias dkpsa='docker ps -a'
alias dkk='func() { docker kill $1;}; func'
alias dkstop='func() { docker stop $1;}; func'
alias dkrm='func() { docker rm $1;}; func'
alias dkrma='docker rm $(docker ps -a -q)'
#同样使用docker inspect命令查看宿主机的挂载目录
#进入容器
alias dkex='func() {docker exec -it $1 /bin/bash;}; func'
#在宿主机执行命令
#docker cp atlas.sh:/opt/apache-atlas.sh-2.1.0/conf/* ~/data/docker-atlas.sh/


# jmeter
alias jmeter='jmeter'
alias dnsclear='sudo killall -HUP mDNSResponder;sudo dscacheutil -flushcache'

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


# deerkids
alias deerkidstestssh='ssh root@test.it.deerkids.com'
alias deerkidsmngtestscp='scp /Users/niufen/code/Git-Deerkids/deerKidsMng/deploy/deerKidsMng.war root@test.it.deerkids.com:/root/tomcat9/webapps/'

alias cdhadoop='cd /usr/local/Cellar/hadoop/3.2.1_1'
alias cdhive='cd /usr/local/Cellar/hive/3.1.2_1'
alias cdhbase='/usr/local/Cellar/hbase/1.3.5'
alias cdsqoop='/usr/local/Cellar/sqoop/1.4.7'
alias cdsolr='/usr/local/Cellar/solr/8.5.1'

alias sb='source ~/.bash_profile'

# ssh
alias hadooptest01='ssh root@hadooptest01.joymo.tech'
alias hadooptest02='ssh root@hadooptest02.joymo.tech'
alias hadooptestgw01='ssh root@hadooptestgw01.joymo.tech'
