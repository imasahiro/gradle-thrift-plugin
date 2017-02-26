package com.github.imasahiro.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class ThriftPlugin implements Plugin<Project> {
    void apply(Project target) {
        def thriftTask = target.task('sphinx', type: ThriftTask)
        def siteTask = target.tasks.find { v -> v.name == 'site' }
        if (siteTask == null) {
            target.task('site').dependsOn(thriftTask)
        }
    }
}

