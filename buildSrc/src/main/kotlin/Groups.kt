import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.kotlinGroup() {
    compile(Deps.kotlinLib)
    compile(Deps.kotlinCss)
}

fun DependencyHandler.serverGroup() {
    compile(Deps.nettyServer)
    compile(Deps.serverCore)
    compile(Deps.htmlBuilder)
}

fun DependencyHandler.logGroup() {
    compile(Deps.logBack)
}

fun DependencyHandler.clientGroup() {
    compile(Deps.clientCore)
    compile(Deps.clientCoreApache)
    compile(Deps.clientCoreJvm)
}

fun DependencyHandler.testGroup() {
    compile(Deps.serverTest)
}

fun DependencyHandler.jsonGroup() {
    compile(Deps.ktorWithGson)
}

private fun DependencyHandler.compile(dependencyNotation: Any): Dependency? =
            add("compile", dependencyNotation)

private fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)