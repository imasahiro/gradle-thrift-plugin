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

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.After
import org.junit.Before
import org.junit.Test

class ThriftTaskTest {
    Project project
    ThriftTask task

    @Before
    void setUp() {
        project = ProjectBuilder.builder().build()
        task = project.task('sphinx', type:ThriftTask)as ThriftTask
    }

    @After
    void tearDown() {
        project.ant.delete(dir:project.projectDir, deleteonexit:true)
    }

    @Test
    void testDefaultProperties() {
        assert !"${task.sourceDirectory}".contains('${')
        assert !"${task.outputDirectory}".contains('${')
        assert task.builder == 'html'
        assert task.tags.isEmpty()
        assert task.verbose
        assert !task.warningsAsErrors
    }

    @Test
    void testOverriddenProperties() {
        task.sourceDirectory "${project.projectDir}/src/my/sphinx"
        task.outputDirectory "${project.buildDir}/my/sphinx"
        task.builder 'mo'
        task.tags 'foo', 'bar'
        task.verbose false
        task.warningsAsErrors true

        assert "${task.getSourceDirectory()}" ==
               "${project.projectDir}${File.separator}src${File.separator}my${File.separator}sphinx"
        assert "${task.getOutputDirectory()}" ==
               "${project.buildDir}${File.separator}my${File.separator}sphinx"

        assert task.builder == 'mo'
        assert task.tags == ["foo", "bar" ]
        assert !task.verbose
        assert task.warningsAsErrors
    }

    @Test
    void testGeneration() {
        project.ant.mkdir(dir:"${task.sourceDirectory}")
        project.ant.copy(todir:"${task.sourceDirectory}"){
            fileset(dir:"${getClass().protectionDomain.codeSource.location.path}/../../../src/site/sphinx")
        }

        task.run()

        assert new File("${task.outputDirectory}/index.html").exists()
        assert new File("${task.outputDirectory}/.doctrees").exists()
        assert new File("${task.outputDirectory}/.buildinfo").exists()
    }
}
