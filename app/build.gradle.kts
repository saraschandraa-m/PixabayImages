plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.saraschandraa.pixabayusers"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.saraschandraa.pixabayusers"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.room)
    implementation(libs.room.ktx)
    annotationProcessor(libs.room.complier)
    kapt(libs.room.complier)
    implementation(libs.sharedPreferences)

    implementation(libs.dagger)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compliler)
    kapt(libs.dagger.android.processor)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.adapter.rxjava)

    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    implementation(libs.glide)
    implementation(libs.android.support)
    implementation(libs.swipeRefresh)
}