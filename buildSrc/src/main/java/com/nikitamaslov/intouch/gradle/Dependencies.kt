package com.nikitamaslov.intouch.gradle

private object Versions {

    val kotlin = "1.3.30"
}

object Libs {

    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    object Android {

        val appCompat = "androidx.appcompat:appcompat:1.1.0-alpha04"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    }

    object Lifecycle {

        private const val version = "2.1.0-alpha04"
        val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
        val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:$version"
    }

    object Navigation {

        private const val version = "2.1.0-alpha02"
        val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
        val ui = "androidx.navigation:navigation-ui-ktx:$version"
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

    object Firebase {

        val core = "com.google.firebase:firebase-core:16.0.9"
        val auth = "com.google.firebase:firebase-auth:17.0.0"
        val firestore = "com.google.firebase:firebase-firestore-ktx:18.2.0"
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

    val navigationSafeArgsGradle =
        "androidx.navigation:navigation-safe-args-gradle-plugin:2.1.0-alpha02"
    val navigationSafeArgs = "androidx.navigation.safeargs.kotlin"

    val googleServicesGradle = "com.google.gms:google-services:4.0.1"
    val googleServices = "com.google.gms.google-services"
}
