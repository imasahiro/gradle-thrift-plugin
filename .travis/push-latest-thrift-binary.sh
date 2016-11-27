#!/bin/sh

NOW=`date +"%Y%m%d%I%M%S"`
BRANCH=thrift

git checkout $BRANCH
git merge master -m "Merge branch 'master' into $BRANCH"
git fetch thrift
git subtree pull --prefix=thrift thrift master
git tag thrift-$NOW
git push origin thrift
git push origin thrift-$NOW
