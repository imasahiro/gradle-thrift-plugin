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

class ThriftPluginTest {
    Project project

    @Before
    void setUp() {
        project = ProjectBuilder.builder().build()
    }

    @After
    void tearDown() {
        project.ant.delete(dir: project.projectDir, deleteonexit: true)
    }

    @Test
    void testTaskRegistration() {
        project.pluginManager.apply 'com.github.imasahiro.gradle.thrift'
        assert project.tasks.sphinx instanceof ThriftTask
        assert project.tasks.site.dependsOn.contains(project.tasks.sphinx)
    }

    @Test
    void testTaskRegistrationWithExistingSiteTask() {
        project.task('site')
        project.pluginManager.apply 'kr.motd.sphinx'
        assert project.tasks.sphinx instanceof ThriftTask
        assert !project.tasks.site.dependsOn.contains(project.tasks.sphinx)
    }
}
