
allprojects {
    group = "net.dankito.kotlin"
    version = "1.0.0-SNAPSHOT"


    ext["sourceCodeRepositoryBaseUrl"] = "github.com/dankito/KmpDateTime"

    ext["projectDescription"] = "Date and time data classes for Kotlin Multiplatform"


    repositories {
        mavenCentral()
    }
}