package com.mandarjoshi.picturelibrary.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.mandarjoshi.picturelibrary.repo.PictureRepository

import androidx.lifecycle.ViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val pictureRepository: PictureRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(aClass: Class<T>): T {
        return PictureViewModel(pictureRepository) as T
    }
}
