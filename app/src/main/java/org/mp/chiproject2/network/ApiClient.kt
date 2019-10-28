package org.mp.chiproject2.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        val BASE_URL = "http://rjtmobile.com/aamir/property-mgmt/"
        @JvmStatic var retrofit: Retrofit? = null
    }

    fun onRetrofitCreate(): Retrofit? {

        if (retrofit == null){
            retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build()
        }

        return retrofit
    }

}