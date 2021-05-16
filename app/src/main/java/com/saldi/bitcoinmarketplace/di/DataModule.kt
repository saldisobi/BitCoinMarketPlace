package com.saldi.bitcoinmarketplace.di

import com.saldi.data.features.bitcoin.repositories.BitcoinRepositoryImpl
import com.saldi.domain.features.bitcoin.repositories.BitcoinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Sourabh on 10/5/21
 */
@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun provideDataRepository(bitcoinRepository: BitcoinRepositoryImpl): BitcoinRepository
}
