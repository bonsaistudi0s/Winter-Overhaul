import org.gradle.language.jvm.tasks.ProcessResources

fun ProcessResources.properties(files: Iterable<String>, vararg properties: Pair<String, Any?>) {
    for ((name, value) in properties) inputs.property(name, value)
    filesMatching(files) {
        expand(properties.toMap())
    }
}