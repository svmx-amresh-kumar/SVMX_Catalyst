#!/bin/bash 
ps -ef | grep Dashboard | grep -v grep | awk '{print $2}' | xargs kill -9 
cd /auto/sahi_pro/userdata/bin/ 
./start_dashboard.sh 