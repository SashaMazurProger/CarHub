plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
//    implementation(libs.hilt.android)
//    kapt(libs.hilt.android.compiler)
//
//    implementation(libs.androidx.activity.v1100)
//    implementation(libs.androidx.fragment)
//    implementation(libs.androidx.lifecycle.viewmodel)
//    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
//    implementation(libs.androidx.savedstate)
}