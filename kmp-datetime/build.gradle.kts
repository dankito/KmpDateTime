import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl


plugins {
    kotlin("multiplatform")

    kotlin("plugin.serialization")
}


kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        // suppresses compiler warning: [EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING] 'expect'/'actual' classes (including interfaces, objects, annotations, enums, and 'actual' typealiases) are in Beta.
        freeCompilerArgs.add("-Xexpect-actual-classes")

        // avoid "variable has been optimised out" in debugging mode
        if (System.getProperty("idea.debugger.dispatch.addr") != null) {
            freeCompilerArgs.add("-Xdebug")
        }
    }


    jvmToolchain(8)

    jvm()


    js {
        binaries.library()

        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    useFirefoxHeadless()
                }
            }
        }

        nodejs {
            testTask {
                useMocha {
                    timeout = "20s" // Mocha times out after 2 s, which is too short for some tests
                }
            }
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    useFirefoxHeadless()
                }
            }
        }
    }


    linuxX64()
    mingwX64()

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    watchosArm64()
    watchosSimulatorArm64()
    tvosArm64()
    tvosSimulatorArm64()

    applyDefaultHierarchyTemplate()


    val kotlinxSerializationVersion: String by project

    // only used for tests
    val assertKVersion: String by project
    val kmpBaseVersion: String by project
    val jacksonVersion: String by project
    val logbackVersion: String by project

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerializationVersion")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))

            implementation("net.codinux.kotlin:kmp-base:$kmpBaseVersion")

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")

            implementation("com.willowtreeapps.assertk:assertk:$assertKVersion")

            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
        }


        jvmMain.dependencies {
            compileOnly("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
        }

        jvmTest.dependencies {
            implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
            implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

            implementation("ch.qos.logback:logback-classic:$logbackVersion")
        }


        val linuxAndMingwMain by creating {
            dependsOn(nativeMain.get())
            linuxMain.get().dependsOn(this)
            mingwMain.get().dependsOn(this)
        }


        val jsCommonMain by creating {
            dependsOn(commonMain.get())
            jsMain.get().dependsOn(this)
            wasmJsMain.get().dependsOn(this)
        }
        val jsCommonTest by creating {
            dependsOn(commonTest.get())
            jsTest.get().dependsOn(this)
            wasmJsTest.get().dependsOn(this)
        }
    }
}


if (File(projectDir, "../gradle/scripts/publish-dankito.gradle.kts").exists()) {
    apply(from = "../gradle/scripts/publish-dankito.gradle.kts")
}