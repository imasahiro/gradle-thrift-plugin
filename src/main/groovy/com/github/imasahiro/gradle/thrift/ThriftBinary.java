/*
 * Copyright 2017 Masahiro Ide
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.github.imasahiro.gradle.thrift;

enum ThriftBinary {
    Thrift_0_9_3_OSX("0.9.3", "osx-x86_64"),
    Thrift_0_9_3_LINUX("0.9.3", "linux-x86_64"),
    Thrift_0_9_3_WINDOWS("0.9.3", "windows-x86_64"),
    Thrift_0_10_0_OSX("0.10.0", "osx-x86_64"),
    Thrift_0_10_0_LINUX("0.10.0", "linux-x86_64"),
    Thrift_0_10_0_WINDOWS("0.10.0", "windows-x86_64"),
    DEFAULT("0.0.0", "");

    private final String version;
    private final String os;

    ThriftBinary(String version, String os) {
        this.version = version;
        this.os = os;
    }

    @Override
    public String toString() {
        return "thrift-" + version + '.' + os;
    }
}