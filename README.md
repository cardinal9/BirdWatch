# TsirbulaWatcher

## Description
Very simple native Android application for recording bird sightings on and offline.
User is able to record new observations about birds they see. New observation will then be added to the list ordered by timestamp, newest first, that is created at the same time when user saves a new observation. User can then scroll through his/her observations list and quickly see the image and attached information of all the observations made.

## Features

  [x] Selecting and attaching an image from devices memory.
  [x] Adding name of the bird and possible notes.
  [x] Adding image from device memory.
  [x] Offline support.

## Built using

  - Android Studio

## Specs:

  - API Level: Nougat 24+
  - Gradle version: 3.5.3
  - Database: Room
  
# Installation

## Android
  Not yet published.
  
## For developers

Clone the project:
  git clone https://github.com/cardinal9/TsirbulaWatcher.git
  
  - Open the project with Android Studio(preferred).
  - Run 'app' either with emulator or real device connected.

In order for the application to work as intended, you have to add these dependecies to your if they happen to be missing project.

Dependencies required - add these lines to your build:gradle(Module: app) file:

### javaCompileOptions - add this inside *defaultConfig* in *android* part.
 - javaCompileOptions {
            annotationProcessorOptions {
                arguments = ["room.schemaLocation":
                                     "$projectDir/schemas".toString()]
            }
        }
        
### sourceSets - above *buildTypes* part.

- sourceSets {
        androidTest.assets.srcDirs +=
                files("$projectDir/schemas".toString())
    }
    
### compileOptions - under the *buildTypes* part.

  - compileOptions {
        sourceCompatibility = 1.8
        targetCompatibility = 1.8
    }

### RecyclerView
  - implementation 'androidx.recyclerview:recyclerview:1.1.0'
  
    implementation 'com.google.android.material:material:1.0.0'
    
### Room database components
  - implementation "androidx.room:room-runtime:$rootProject.roomVersion"
    implementation "android.arch.persistence.room:runtime:$rootProject.roomVersion"
    annotationProcessor "androidx.room:room-compiler:$rootProject.roomVersion"
    annotationProcessor "android.arch.persistence.room:compiler:$rootProject.roomVersion"
    androidTestImplementation "androidx.room:room-testing:$rootProject.roomVersion"
    androidTestImplementation "android.arch.persistence.room:testing:$rootProject.roomVersion"
    
### Lifecycle components
  - implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0-rc03'
  
    annotationProcessor 'androidx.lifecycle:lifecycle-compiler:2.2.0-rc03'
    
### UI and testing
  - implementation "com.google.android.material:material:$rootProject.materialVersion"

// Testing
    androidTestImplementation "androidx.arch.core:core-testing:$rootProject.coreTestingVersion"

    implementation 'com.squareup.picasso:picasso:2.71828'
    
With these gradle dependencies in place we sould be able to run the application as intended.


## License

