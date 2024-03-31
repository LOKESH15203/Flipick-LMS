plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {

    // Add compile options to enable deprecation warnings
//    compileOptions {
//        lint.warning
//        "deprecation"
//    }

    namespace = "com.example.flipicklms"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.flipicklms"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.airbnb.android:lottie:6.1.0")
    implementation ("com.android.volley:volley:1.2.1")
    implementation("androidx.viewpager2:viewpager2:1.1.0-beta02")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("me.relex:circleindicator:2.1.6")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.mlkit:common:18.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.media3:media3-exoplayer:1.2.1")
    implementation ("androidx.media3:media3-ui:1.2.1")
    implementation ("androidx.media3:media3-common:1.2.1")
    implementation ("androidx.media3:media3-session:1.2.1")

}