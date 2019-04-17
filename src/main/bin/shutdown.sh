#!/usr/bin/env bash

############################################
######### @author: yinguijin ###############
######### date: 2019-4-16 ##################
######### version: V1.0.0 ##################
############################################

appName=traffic-signal-utcsystem

pid=`jps -lv | grep -v grep | grep -v shutdown | grep "${appName}"  | sed -n  '1P' | awk '{print $1}'`

if [ -z ${pid} ];then
    echo "process not found..."
else
    kill -9 ${pid}
fi

echo "shutdown app success."
