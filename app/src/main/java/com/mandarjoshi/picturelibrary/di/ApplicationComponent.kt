package com.mandarjoshi.picturelibrary.di

import com.mandarjoshi.picturelibrary.ui.PictureMainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ApplicationModule::class])
interface ApplicationComponent {
    fun inject(pictureMainFragment: PictureMainFragment)
}
