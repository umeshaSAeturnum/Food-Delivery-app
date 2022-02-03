package com.example.foodapp.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.media.MediaCodec.MetricsConstants.MODE
import android.net.wifi.WifiConfiguration.AuthAlgorithm.SHARED
import androidx.room.Room
import com.example.foodapp.db.OrderDB
import com.example.foodapp.other.Constants.KEY_IS_LOGIN
import com.example.foodapp.other.Constants.KEY_USER_ID
import com.example.foodapp.other.Constants.ORDER_DATABASE_NAME
import com.example.foodapp.other.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module

@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providingOrderDatabase(
        //by annotating as ApplicationContext Dagger knows that it needs to take the application as the context
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        OrderDB::class.java,
        ORDER_DATABASE_NAME

    ).build()


    @Singleton
    @Provides
    fun provideFoodDao(db:OrderDB) = db.getFoodDao()

    @Singleton
    @Provides
    fun provideOrderDao(db:OrderDB) = db.getOrderDao()

    @Singleton
    @Provides
    fun provideUserDao(db:OrderDB) = db.getUserDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app : Context) =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)




}