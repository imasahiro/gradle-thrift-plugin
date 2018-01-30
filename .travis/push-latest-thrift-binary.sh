#!/bin/sh

NOW=`date +"%Y%m%d%H%M%S"`
BRANCH=thrift

# git remote add thrift https://github.com/imasahiro/thrift.git
git checkout $BRANCH
git merge master -m "Merge branch 'master' into $BRANCH"
git fetch thrift
git subtree pull --prefix=thrift thrift 0.11.0
git push origin thrift

git tag -a thrift-$NOW -m "thrift snapshot $NOW"
git push --tags origin master
git checkout master
