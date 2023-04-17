package com.rijksmuseum.task.util.validator

interface Validator<T> {

    fun validate(param: T): Errors
}