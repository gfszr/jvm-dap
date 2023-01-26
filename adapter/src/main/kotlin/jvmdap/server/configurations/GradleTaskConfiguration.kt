package jvmdap.server.configurations

import java.nio.file.Path

class GradleTaskConfiguration(
    val gradleTask: String,
    val projectRoot: Path,
    val args: Map<String, String>,
    val env: Map<String, String>,
) {
}