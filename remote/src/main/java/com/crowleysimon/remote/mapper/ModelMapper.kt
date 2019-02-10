package com.crowleysimon.remote.mapper

interface ModelMapper<in R, out E> {

    fun mapFromResponse(response: R): E
}