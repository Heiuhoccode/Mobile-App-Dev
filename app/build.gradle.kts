plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.moblie_app_dev"
    compileSdk {
        version = release(36)
    }
    sourceSets {
        getByName("main") {
            res.srcDirs(
                "src/main/res/layouts/layout_auth",
                "src/main/res/layouts/layout_kid",
                "src/main/res/layouts/layout_parent",
                "src/main/res/layouts/layout_teacher",
                "src/main/res/layouts/layout_admin",
                "src/main/res"
            )
        }
    }
    defaultConfig {
        applicationId = "com.example.moblie_app_dev"
        minSdk = 24
        targetSdk = 36
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
}