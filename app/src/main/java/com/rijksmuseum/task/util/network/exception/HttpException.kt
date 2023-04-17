package com.rijksmuseum.task.util.network.exception

class HttpException(
    val url: String,
    val code: Int,
    override val message: String,
    val errorBody: String?
) : RuntimeException(message)