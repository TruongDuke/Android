package com.example.currencyconverter
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyApi {
    @GET("v4/latest/USD")
    fun getExchangeRates() : Call<CurrencyResponse>
}

