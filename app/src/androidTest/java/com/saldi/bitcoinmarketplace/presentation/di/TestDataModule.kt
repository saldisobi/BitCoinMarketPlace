package com.saldi.bitcoinmarketplace.presentation.di

import com.saldi.bitcoinmarketplace.di.DataModule
import com.saldi.bitcoinmarketplace.presentation.testutil.TestDataRepository
import com.saldi.domain.features.bitcoin.repositories.BitcoinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
abstract class TestDataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: TestDataRepository): BitcoinRepository
}
