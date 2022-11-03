package uz.gita.bookapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bookapp.domain.booksRepository.BooksRepository
import uz.gita.bookapp.domain.booksRepository.booksRepositoryImpl.BooksRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BooksRepositoryModule {

    @[Binds Singleton]
    fun getRepositoryImpl(repositoryImpl: BooksRepositoryImpl): BooksRepository

}