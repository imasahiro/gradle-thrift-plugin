#!/bin/sh
set -ev

BINARY_SUFFIX=""
if ( test "`uname -s`" = "Darwin" )
then
    BINARY_SUFFIX="osx-x86_64"
else
    BINARY_SUFFIX="linux-x86_64"
fi

CMAKE_FLAGS="-DBUILD_TESTING=OFF -DBUILD_EXAMPLES=OFF -DBUILD_LIBRARIES=OFF"
MAKEPROG=make

if ninja --version  >/dev/null 2>&1; then
  MAKEPROG=ninja
  CMAKE_FLAGS="-GNinja $CMAKE_FLAGS"
fi

BUILD_DIR=thrift/cmake_build
mkdir -p $BUILD_DIR && cd $BUILD_DIR
cmake $CMAKE_FLAGS ../
for LIB in $BUILD_LIBS; do
  if ! grep "^BUILD_${LIB}:BOOL=ON$" CMakeCache.txt ; then
    echo "failed to configure $LIB"
    exit 1
  fi
done
$MAKEPROG -j3
mv bin/thrift bin/thrift-$BINARY_SUFFIX
