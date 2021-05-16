package com.saldi.bitcoinmarketplace.di

import com.saldi.data.features.bitcoin.remote.BitcoinSourceRemote
import com.saldi.data.features.bitcoin.remote.BitcoinSourceRemoteImpl
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
interface RemoteModule {
    @Binds
    @Singleton
    fun provideBitcoinSourceRemote(bitcoinSourceRemote: BitcoinSourceRemoteImpl): BitcoinSourceRemote

}