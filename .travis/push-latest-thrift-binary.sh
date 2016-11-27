#!/bin/sh

NOW=`date +"%Y%m%d%H%M%S"`
BRANCH=thrift

git checkout $BRANCH
git merge master -m "Merge branch 'master' into $BRANCH"
git fetch thrift
git subtree pull --prefix=thrift thrift master
git push origin thrift

git tag -a thrift-$NOW -m "thrift snapshot $NOW"
git push --tags origin master
git checkout master
