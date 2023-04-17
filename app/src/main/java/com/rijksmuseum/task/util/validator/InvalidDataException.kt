package com.rijksmuseum.task.util.validator

class InvalidDataException(errors: Errors) : RuntimeException(
    errors.errors().joinToString {
        "${it.tag} -> ${it.errorMessage} \n"
    }
)