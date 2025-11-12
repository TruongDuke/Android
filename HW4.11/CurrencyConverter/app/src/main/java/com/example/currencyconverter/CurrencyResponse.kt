package com.example.currencyconverter

data class CurrencyResponse (
    val base: String,
    val rates: Map<String, Double>
)