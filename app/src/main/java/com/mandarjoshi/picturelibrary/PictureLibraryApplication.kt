package com.mandarjoshi.picturelibrary

import android.app.Application
import com.mandarjoshi.picturelibrary.di.ApplicationComponent
import com.mandarjoshi.picturelibrary.di.DaggerApplicationComponent

class PictureLibraryApplication: Application() {
    var appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}
