package com.saldi.bitcoinmarketplace.di

import android.content.Context
import com.saldi.bitcoinmarketplace.BuildConfig
import com.saldi.bitcoinmarketplace.di.constants.DiConstants.CACHE_SIZE
import com.saldi.bitcoinmarketplace.di.constants.DiConstants.CONNECTION_TIMEOUT_NAME
import com.saldi.bitcoinmarketplace.di.constants.DiConstants.CONNECTION_TIMEOUT_VALUE
import com.saldi.bitcoinmarketplace.di.constants.DiConstants.READ_TIMEOUT_NAME
import com.saldi.bitcoinmarketplace.di.constants.DiConstants.READ_TIMEOUT_VALUE
import com.saldi.data.features.bitcoin.network.BitcoinApiServiceFactory
import com.saldi.data.features.bitcoin.network.BitcoinService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by Sourabh on 15/5/21
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideBitcoinService(
        cache: Cache,
        enableLogs: Boolean,
        @Named(CONNECTION_TIMEOUT_NAME) connectionTimeout: Long,
        @Named(READ_TIMEOUT_NAME) readTimeout: Long,
    ): BitcoinService =
        BitcoinApiServiceFactory.createApiService(enableLogs, cache, connectionTimeout, readTimeout)

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext appContext: Context): Cache {
        val cacheSize = CACHE_SIZE
        return Cache(appContext.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun enableLogs(): Boolean {
        return BuildConfig.DEBUG
    }

    @Provides
    @Singleton
    @Named(CONNECTION_TIMEOUT_NAME)
    fun provideConnectionTimeout(): Long {
        return CONNECTION_TIMEOUT_VALUE
    }

    @Provides
    @Singleton
    @Named(READ_TIMEOUT_NAME)
    fun provideReadTimeout(): Long {
        return READ_TIMEOUT_VALUE
    }


}
