#!/bin/bash
if [ $# -ne 1 ] 
then
	echo "Usage execute.sh <sqlFile>"
exit 1
fi

JAVA_HOME=/cygdrive/c/Program\ Files/Java/jdk1.8.0_144/
export JAVA_HOME
PATH=/cygdrive/c/groovy-2.4.12/bin:$PATH
export PATH

CLASSPATH=":"

for jarFile in $(find ../lib -type f -name "*.jar")
do
CLASSPATH=$jarFile:$CLASSPATH
done

groovy -cp $CLASSPATH query.groovy $1
