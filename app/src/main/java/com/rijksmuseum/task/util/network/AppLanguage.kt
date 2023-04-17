package com.rijksmuseum.task.util.network

enum class AppLanguage(val code: String) {
    NETHERLANDS("nl"),
    ENGLISH("en");

    companion object {
        fun findByOrdinal(ordinal: Int): AppLanguage? {
            return values().find { it.ordinal == ordinal }
        }
    }
}