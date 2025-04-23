package net.dankito.datetime

import kotlin.annotation.AnnotationTarget.*

@Target(
    CLASS,
    ANNOTATION_CLASS,
    PROPERTY,
    FIELD,
    LOCAL_VARIABLE,
    VALUE_PARAMETER,
    CONSTRUCTOR,
    FUNCTION,
    PROPERTY_GETTER,
    PROPERTY_SETTER,
    TYPEALIAS
)
@Retention(AnnotationRetention.BINARY)
annotation class ExperimentalDateTimeApi
