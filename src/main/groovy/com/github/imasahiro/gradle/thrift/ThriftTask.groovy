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
package com.github.imasahiro.gradle.thrift

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.*

class ThriftTask extends DefaultTask {
    def sourceDirectory = { "${project.projectDir}${File.separator}src${File.separator}thrift" }
    def outputDirectory = { "${project.buildDir}${File.separator}thrift" }
    def generator = { "java" }
    def options = { Collections.emptyList() }
    def verbose = { false }
    def recurse = { true }
    def strict = { false }
    def debug = { false }
    def thriftVersion = { ThriftBinary.DEFAULT }
    def thriftExecutable = { "thrift" }

    @Input
    ThriftBinary getThriftVersion() {
        (thriftVersion instanceof ThriftBinary ? thriftVersion : thriftVersion())
    }

    @Input
    String getThriftExecutable() {
        (thriftExecutable instanceof CharSequence ? thriftExecutable : thriftExecutable()).toString()
    }

    @SkipWhenEmpty
    @InputDirectory
    File getSourceDirectory() {
        project.file(sourceDirectory)
    }

    @OutputDirectory
    File getOutputDirectory() {
        project.file(outputDirectory)
    }

    @Input
    String getGenerator() {
        (this.generator instanceof CharSequence ? this.generator : generator()).toString()
    }

    @Input
    List<String> getOptions() {
        (this.options instanceof Iterable ? this.options : options()).toList()
    }

    void options(String... options) {
        this.options = Arrays.asList(options)
    }

    void option(Object options) {
        this.options = options
    }

    @Input
    boolean isVerbose() {
        (verbose instanceof Boolean ? verbose : verbose())
    }

    @Input
    boolean isRecurse() {
        (recurse instanceof Boolean ? recurse : recurse())
    }

    @Input
    boolean isStrict() {
        (strict instanceof Boolean ? strict : strict())
    }

    @Input
    boolean isDebug() {
        (debug instanceof Boolean ? debug : debug())
    }

    @TaskAction
    def run() {
        def result = project.exec {
            commandLine getThriftCmdLine()
        }

        def exitCode = result.exitValue
        if (exitCode != 0) {
            throw new GradleException("Failed to compile ${source}, exit=${exitCode}")
        }
    }

    /**
     * Build the Sphinx Command line options.
     */
    private List<String> getThriftCmdLine() {
        final List<String> command = []
        ThriftBinary binary = getThriftVersion()
        if (binary == ThriftBinary.DEFAULT) {
            command.add(getThriftExecutable())
        } else {
            command.add(binary.toString())
        }
        command.add("--gen")
        List<String> options = getOptions()
        command.add(getGenerator() + options.isEmpty() ? "" : options.join(","))
        if (isRecurse()) {
            command.add("-recurse")
        }
        if (isVerbose()) {
            command.add("-verbose")
        }
        if (isStrict()) {
            command.add("-strict")
        }
        if (isDebug()) {
            command.add("-debug")
        }
        command.add("-out")
        command.add(getOutputDirectory().getAbsolutePath())
        command.add("-I")
        command.add(getSourceDirectory().getAbsolutePath())
        return command;
    }
}
