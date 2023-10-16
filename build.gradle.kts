// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id("org.jetbrains.kotlin.kapt") version "1.8.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("com.android.library") version "8.1.0" apply false
}

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("com.huawei.agconnect:agcp:1.6.0.300")
    }
}
