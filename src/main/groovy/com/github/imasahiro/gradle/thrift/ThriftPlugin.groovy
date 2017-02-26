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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.language.base.ProjectSourceSet

class ThriftPlugin implements Plugin<Project> {
    private static final Closure<String> thriftTaskName = { sourceSet ->
        sourceSet.name.equals('main') ? "compileThrift" : "compile${sourceSet.name.capitalize()}Thrift"
    }

    private static final Closure<String> javaTaskName = { sourceSet ->
        sourceSet.name.equals('main') ? "compileJava" : "compile${sourceSet.name.capitalize()}Java"
    }

    void apply(Project target) {
        for (projectSourceSet in project.sourceSets) {
            def thriftTaskName = thriftTaskName(projectSourceSet)

            project.tasks.create(
                    name: thriftTaskName, overwrite: true,
                    description: "Compiles Thrift Source for ${projectSourceSet.name} source set",
                    type: ThriftTask) {
                sourceSet = projectSourceSet
            }
            logger.info("Adding thrift task: {}", thriftTaskName);
            addDependency(project, projectSourceSet);
        }
    }

    void addDependency(Project project, ProjectSourceSet sourceSet) {
        if (project.plugins.hasPlugin('java'))
            makeAsDependency(sourceSet)
        else {
            project.plugins.whenPluginAdded { plugin ->
                if (plugin instanceof JavaPlugin) {
                    makeAsDependency(sourceSet)
                }
            }
        }
    }

    void makeAsDependency(ProjectSourceSet sourceSet) {
        def javaTaskName = javaTaskName(sourceSet)
        def thriftTaskName = thriftTaskName(sourceSet)
        project.tasks[thriftTaskName].setDependsOn(project.tasks[javaTaskName].dependsOn)
        project.tasks[thriftTaskName].dependsOn(project.tasks[thriftTaskName].aspectpath)
        project.tasks[thriftTaskName].dependsOn(project.tasks[thriftTaskName].ajInpath)
        project.tasks[javaTaskName].deleteAllActions()
        project.tasks[javaTaskName].dependsOn(project.tasks[thriftTaskName])
    }
}

