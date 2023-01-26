package jvmdap.server.debuggees

import jvmdap.server.configurations.LaunchMainClassConfiguration
import java.nio.file.Path

class ClassLauncher(config: LaunchMainClassConfiguration): ProcessLauncher(createProcessBuilder(config))
{
    companion object {
       fun createProcessBuilder(config: LaunchMainClassConfiguration): ProcessBuilder {
            return ProcessBuilder("${config.javaExec}",
                "-cp", config.classPaths.joinToString(":"),
                config.mainClass.toString(),
                *config.args,
            ).also {
                it.directory(config.cwd.toFile())
                config.env.forEach { (key, value) ->
                    it.environment()[key] = value
                }
            }
       }
    }
}