package com.rijksmuseum.task.util.validator

class Errors(val target: Any) {

    private val errors = mutableMapOf<Any?, String>()

    fun addError(errorMessage: String, tag: Any? = null) {
        errors[tag] = errorMessage
    }

    fun addErrors(other: List<Field>) {
        val map = other.associate {
            Pair(it.tag, it.errorMessage)
        }
        errors.putAll(map)
    }

    fun errors(): List<Field> {
        return errors.map {
            Field(it.value, it.key)
        }
    }

    fun hasError(): Boolean {
        return errors.isNotEmpty()
    }
}
