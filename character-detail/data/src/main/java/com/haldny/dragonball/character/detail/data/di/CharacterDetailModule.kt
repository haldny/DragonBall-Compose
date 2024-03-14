package com.haldny.dragonball.character.detail.data.di

import com.haldny.dragonball.character.detail.data.CharacterDetailRepositoryImpl
import com.haldny.dragonball.character.detail.data.api.CharacterDetailApi
import com.haldny.dragonball.character.detail.domain.CharacterDetailRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterDetailModule {

    @Binds
    abstract fun bindsCharacterDetailRepository(repository: CharacterDetailRepositoryImpl) : CharacterDetailRepository

    companion object {
        @Provides
        @Singleton
        fun provideCharactersApi(retrofit: Retrofit) : CharacterDetailApi
                = retrofit.create(CharacterDetailApi::class.java)
    }
}