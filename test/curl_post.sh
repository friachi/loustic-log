#!/bin/bash

if [ $# -ne 3 ] 
then
	echo "Usage curl_post.sh <jsonFile> <port> <path>"
exit 1
fi

curl -H "Content-Type: application/json" -d @$1 -X POST http://localhost:$2/$3
