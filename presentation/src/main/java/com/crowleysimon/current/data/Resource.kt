package com.crowleysimon.current.data

/**
 * Represents a task that is either loading, successful or in an error state.
 */
sealed class Resource< out T>(open val data: T?)

/**
 * Represents a resource from an operation that is successful. A successful resource will always contain
 * a result. see [data]
 */
data class SuccessResource<out T>(override val data: T) : Resource<T>(data)

/**
 * Represents a resource from an operation that has failed. An error resource will always contain
 * a [throwable] detailing the failure and optionally some [data] if available.
 */
data class ErrorResource<out T>(val throwable: Throwable, override val data: T? = null) : Resource<T>(data)

/**
 * Represents a resource from an operation that is currently loading.
 * A loading resource can optionally contain [data] if available.
 */
data class LoadingResource<out T>(override val data: T? = null) : Resource<T>(data)