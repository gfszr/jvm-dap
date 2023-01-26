package jvmdap.server.configurations

import java.nio.file.Path

class LaunchMainClassConfiguration(
    val javaExec: Path,
    val mainClass: Path,
    val classPaths: Array<String>,
    val args: Array<String>,
    val env: Map<String, String>,
    val cwd: Path,
) {
}