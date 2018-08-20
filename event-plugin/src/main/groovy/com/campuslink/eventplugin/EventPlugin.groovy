package com.campuslink.eventplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class EventPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        println("plugin==========:" + project.getExtensions().getByName("android"))
        project.android.registerTransform(new TransformLogic(project))
    }
}