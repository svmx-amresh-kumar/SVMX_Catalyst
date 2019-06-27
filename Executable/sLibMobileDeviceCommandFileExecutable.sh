#!/bin/bash 
 cd /usr/local/Cellar/libimobiledevice/ 
filename=$(ls)
echo $filename  
$filename/bin/idevicedate > /auto/SVMX_Catalyst/Executable/tempFileToRead.txt