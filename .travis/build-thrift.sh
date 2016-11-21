#!/bin/sh
(cd thrift &&
    ./thrift/build/docker/scripts/cmake.sh &&
    ls cmake_build)
