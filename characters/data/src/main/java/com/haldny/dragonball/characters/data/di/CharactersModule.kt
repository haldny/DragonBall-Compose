package com.haldny.dragonball.characters.data.di

import com.haldny.dragonball.characters.data.CharactersRepositoryImpl
import com.haldny.dragonball.characters.data.api.CharactersApi
import com.haldny.dragonball.characters.domain.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CharactersModule {

    @Binds
    abstract fun bindsCharactersRepository(repository: CharactersRepositoryImpl) : CharactersRepository

    companion object {
        @Provides
        @Singleton
        fun provideCharactersApi(retrofit: Retrofit) : CharactersApi
                = retrofit.create(CharactersApi::class.java)
    }
}
