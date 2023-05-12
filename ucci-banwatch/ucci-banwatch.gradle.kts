version = "0.1.0"

project.extra["PluginName"] = "Ucci banwatch"
project.extra["PluginDescription"] = "Sends a webhook when the banned screen is shown"

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
}

tasks {
    jar {
        manifest {
            attributes(mapOf(
                    "Plugin-Version" to project.version,
                    "Plugin-Id" to nameToId(project.extra["PluginName"] as String),
                    "Plugin-Provider" to project.extra["PluginProvider"],
                    "Plugin-Description" to project.extra["PluginDescription"],
                    "Plugin-License" to project.extra["PluginLicense"]
            ))
        }
    }
}
