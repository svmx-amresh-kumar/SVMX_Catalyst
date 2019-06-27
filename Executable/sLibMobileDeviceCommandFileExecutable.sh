#!/bin/bash 
 cd /usr/local/Cellar/libimobiledevice/ 
filename=$(ls)
echo $filename  
$filename/bin/ideviceinfo > /auto/SVMX_Catalyst/Executable/tempFileToRead.txt