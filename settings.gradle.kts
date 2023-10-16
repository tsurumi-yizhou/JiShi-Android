pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://developer.huawei.com/repo/") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://developer.huawei.com/repo/") }
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "JiShi"
include(":app")
