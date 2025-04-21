package net.dankito.datetime

/**
 * The precision of the timestamp since Epoch of the underlying platform.
 *
 * (Milliseconds for JVM, JS and WasmJS; Seconds for Linux, Windows and Apple systems.)
 */
enum class TimeSinceEpochPrecision {
    Seconds,
    Milliseconds
}