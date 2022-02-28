package com.mandarjoshi.picturelibrary.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mandarjoshi.picturelibrary.model.Manifest
import com.mandarjoshi.picturelibrary.model.PictureContainer

import com.mandarjoshi.picturelibrary.util.Resource
import kotlinx.coroutines.Dispatchers

open class PictureRepository(private val pictureService: PictureService) {

    fun getGroups(): LiveData<Resource<Manifest?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data=pictureService.getGroups()))
            }catch (exception: Exception){
                emit(Resource.error(data=null,message = "Error"))
            }
            //catching high level exception for now
            // detekt static code analysis is catching it.
        }
    }

    fun getPictureDetails(pictureContainer: PictureContainer): LiveData<Resource<PictureContainer?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            try {
                pictureContainer.pictureDetails = pictureService.getPictureDetails(pictureContainer.pictureId)
                emit(Resource.success(data=pictureContainer))
            }catch (exception: Exception){
                emit(Resource.error(data=null,message = exception.localizedMessage))
            }
        }
    }
}
