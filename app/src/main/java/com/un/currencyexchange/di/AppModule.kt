package com.un.currencyexchange.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.un.currencyexchange.R
import com.un.currencyexchange.network.Api
import com.un.currencyexchange.network.ErrorInterceptor
import com.un.currencyexchange.util.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
     @Singleton
     @Provides
     fun provideRetrofitInstance(): Retrofit {
         val interceptor = HttpLoggingInterceptor()
         interceptor.level = HttpLoggingInterceptor.Level.BODY

         val client = OkHttpClient.Builder()
             .addInterceptor(Interceptor { chain ->
                 var request = chain.request()
                 val url = request.url.newBuilder().build()
                 request = request.newBuilder().url(url).build()
                 chain.proceed(request)
             })
             .addInterceptor(interceptor)
             .addInterceptor(ErrorInterceptor())
             .build()

         return Retrofit.Builder()
             .baseUrl(Constants.BASE_URL)
             .client(client)
             .addConverterFactory(GsonConverterFactory.create())
             .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
             .build()
     }

     @Singleton
     @Provides
     fun provideMainApi(retrofit: Retrofit) = retrofit.create(Api::class.java)

     @Singleton
     @Provides
     fun provideRequestOptions(): RequestOptions {
         return RequestOptions()
             .placeholder(R.drawable.ic_launcher_foreground)
             .error(R.drawable.ic_launcher_background)
     }

     @Singleton
     @Provides
     fun provideGlideInstance(app: Application, requestOptions: RequestOptions): RequestManager {
         return Glide.with(app)
             .setDefaultRequestOptions(requestOptions)
     }


    @Singleton
    @Provides
    fun getDefaultSharadPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("com.un.currencyexchange.di", Context.MODE_PRIVATE)
    }
}
