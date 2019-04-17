#!/bin/sh

############################################
######### @author: yinguijin ###############
######### date: 2019-4-16 ##################
######### version: V1.0.0 ##################
############################################

#set JAVA_HOME
#JAVA_HOME=/usr/local/java

noJavaHome=false
appName=traffic-signal-utcsystem
appVersion="1.0"
appJar="$appName-*.jar"

# 日志目录
logDir="/home/mapabc/$appName"

# SpringEnv profile
env=$1

if [ ! -d "$logDir" ] ; then
    mkdir -p "$logDir"
fi

if [ -z "$JAVA_HOME" ] ; then
    noJavaHome=true
fi
if [ ! -e "$JAVA_HOME/bin/java" ] ; then
    noJavaHome=true
fi
if ${noJavaHome} ; then
    echo
    echo "Error: JAVA_HOME environment variable is not set."
    echo
    exit 1
fi
#==============================================================================



#set JAVA_OPTS
# 备注：测试环境低配运行, 可自行根据需要调整JVM参数
if [ "Xdev" == "X"${env} ]; then
    JAVA_OPTS="-server -Xms2048m -Xmx2048m -Xmn512m -Xss1024k"
else
    JAVA_OPTS="-server -Xms8192m -Xmx8192m -Xmn3072m -Xss1024k"
fi

#performance Options
JAVA_OPTS="$JAVA_OPTS -XX:+AggressiveOpts"
JAVA_OPTS="$JAVA_OPTS -XX:+UseBiasedLocking"
JAVA_OPTS="$JAVA_OPTS -XX:+UseFastAccessorMethods"
JAVA_OPTS="$JAVA_OPTS -XX:+DisableExplicitGC"
JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC"
JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"
JAVA_OPTS="$JAVA_OPTS -XX:+CMSParallelRemarkEnabled"
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError"
JAVA_OPTS="$JAVA_OPTS -XX:HeapDumpPath=$logDir/heapdump.log"

#set HOME jar目录
APP_DIR=`pwd`
cd `dirname "$0"`/..
APP_HOME=`pwd`
cd ${APP_DIR}
if [ -z "$APP_HOME" ] ; then
    echo
    echo "Error: APP_HOME environment variable is not defined correctly."
    echo
    exit 1
fi
#==============================================================================

#set CLASSPATH
APP_CLASSPATH="$APP_HOME/conf:$APP_HOME/lib/classes"
for i in "$APP_HOME"/lib/*.jar
do
    APP_CLASSPATH="$APP_CLASSPATH:$i"
done
#==============================================================================


#startup
RUN_CMD="nohup java -jar -Dspring.profiles.active=$env"
RUN_CMD="$RUN_CMD $JAVA_OPTS $APP_HOME/lib/$appJar"
RUN_CMD="$RUN_CMD --spring.config.location=$APP_HOME/conf/"
RUN_CMD="$RUN_CMD -classpath \"$APP_CLASSPATH\""
RUN_CMD="$RUN_CMD >> $logDir/startup.log 2>&1 &"
echo ${RUN_CMD}
eval ${RUN_CMD}
echo "====start app complete===="
echo $?
#==============================================================================
