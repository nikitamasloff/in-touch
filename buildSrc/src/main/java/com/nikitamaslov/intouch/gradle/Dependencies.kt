package com.nikitamaslov.intouch.gradle

private object Versions {

    val kotlin = "1.3.30"
}

object Libs {

    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    object Android {

        val appCompat = "androidx.appcompat:appcompat:1.1.0-alpha04"
    }

    object Rx {

        val java = "io.reactivex.rxjava2:rxjava:2.2.8"
        val kotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"
        val android = "io.reactivex.rxjava2:rxandroid:2.1.1"
    }

    object Dagger {

        private const val version = "2.22.1"
        val core = "com.google.dagger:dagger:$version"
        val compiler = "com.google.dagger:dagger-compiler:$version"
        val androidSupport = "com.google.dagger:dagger-android-support:$version"
        val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
    }
}

object Plugins {

    val androidApplication = "com.android.application"
    val androidLibrary = "com.android.library"

    val kotlinAndroid = "kotlin-android"
    val kotlinAndroidExtensions = "kotlin-android-extensions"
    val kotlinKapt = "kotlin-kapt"

    val androidGradle = "com.android.tools.build:gradle:3.4.0"
    val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}
