#!/bin/sh
(cd thrift &&
    ./build/docker/scripts/cmake.sh &&
    ls cmake_build)
