package fc.retrofit.mock.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class RetrofitMockPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("retrofitMock", RetrofitMockExtension)

        boolean disableRetrofitMockPlugin = false
        Properties properties = new Properties()
        if (project.rootProject.file('local.properties').exists()) {
            properties.load(project.rootProject.file('local.properties').newDataInputStream())
            disableRetrofitMockPlugin = Boolean.parseBoolean(properties.getProperty("retrofitMock.disablePlugin", "false"))
        }

        if (!disableRetrofitMockPlugin) {
            AppExtension appExtension = project.extensions.findByType(AppExtension.class)
            appExtension.registerTransform(new RetrofitMockTransform(project))
        }
    }
}
