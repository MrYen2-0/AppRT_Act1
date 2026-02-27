package com.yuen.encuestasockets.core.di

import com.yuen.encuestasockets.feature.auth.data.repository.AuthRepositoryImpl
import com.yuen.encuestasockets.feature.auth.domain.repository.AuthRepository
import com.yuen.encuestasockets.feature.encuestas.data.repository.EncuestasRepositoryImpl
import com.yuen.encuestasockets.feature.encuestas.domain.repository.EncuestasRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindEncuestasRepository(impl: EncuestasRepositoryImpl): EncuestasRepository
}
