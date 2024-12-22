plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.jinglecode"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jinglecode"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}





dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.airbnb.android:lottie:5.0.3")
    implementation ("com.google.android.material:material:1.8.0")
    implementation ("com.google.android.material:material:1.8.0") // Or latest
    implementation ("androidx.compose.ui:ui-text-google-fonts:1.3.0")  // Required for fonts



}