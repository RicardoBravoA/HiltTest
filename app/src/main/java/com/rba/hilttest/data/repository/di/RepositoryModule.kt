package com.rba.hilttest.data.repository.di

import com.rba.hilttest.data.repository.MoviePopularRepository
import com.rba.hilttest.data.repository.MoviePopularRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieRepository(repository: MoviePopularRepositoryImpl): MoviePopularRepository

}
