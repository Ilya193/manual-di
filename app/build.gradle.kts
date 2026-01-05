plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "ru.ikom.manual_di"
    compileSdk = 36

    defaultConfig {
        applicationId = "ru.ikom.manual_di"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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

    implementation(project(":core:ui"))
    implementation(project(":core:domain:domain-messages"))
    implementation(project(":core:data:data-messages"))

    implementation(project(":features:feature-root"))

    implementation(project(":features:feature-messages:api"))
    implementation(project(":features:feature-messages:impl"))

    implementation(project(":features:feature-detail-message:api"))
    implementation(project(":features:feature-detail-message:impl"))

    implementation("com.github.terrakok:cicerone:7.1")
}