package com.mandarjoshi.picturelibrary.di

import com.mandarjoshi.picturelibrary.repo.PictureService
import dagger.Module
import dagger.Provides

import retrofit2.Retrofit
import com.mandarjoshi.picturelibrary.repo.PictureRepository
import com.mandarjoshi.picturelibrary.viewmodel.ViewModelFactory


@Module
class ApplicationModule {
    @Provides
    fun providesPictureService(
        retrofit: Retrofit
    ): PictureService {
        return retrofit.create(PictureService::class.java)
    }

    @Provides
    fun providesPictureRepository(pictureService: PictureService): PictureRepository {
        return PictureRepository(pictureService)
    }

    @Provides
    fun providesViewModelFactory(pictureRepository: PictureRepository): ViewModelFactory {
        return ViewModelFactory(pictureRepository)
    }
}
