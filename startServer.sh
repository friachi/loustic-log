#!/bin/bash

JAVA_HOME=/cygdrive/c/Program\ Files/Java/jdk1.8.0_144/
export JAVA_HOME
PATH=/cygdrive/c/groovy-2.4.12/bin:$PATH
export PATH

CLASSPATH=":"

for jarFile in $(find ./lib -type f -name "*.jar")
do
CLASSPATH=$jarFile:$CLASSPATH
done

#nohup groovy -Dgrape.root=./lib/ -Dratpack.port=12000 ratpack.groovy &
groovy -cp $CLASSPATH -Dgrape.root=./lib/ -Dratpack.port=12000 ratpack.groovy 
