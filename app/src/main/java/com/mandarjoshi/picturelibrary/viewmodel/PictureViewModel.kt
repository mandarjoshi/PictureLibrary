package com.mandarjoshi.picturelibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mandarjoshi.picturelibrary.model.Manifest

import com.mandarjoshi.picturelibrary.model.Album
import com.mandarjoshi.picturelibrary.model.PictureContainer
import com.mandarjoshi.picturelibrary.repo.PictureRepository

import com.mandarjoshi.picturelibrary.util.Resource

class PictureViewModel(private val pictureRepository: PictureRepository) : ViewModel() {

    //since caching is not implemented, it is cached here
    private lateinit var albums: MutableList<Album>
    private var groups: Manifest? = null
    private var currentGroupIndex = 0

    fun getPictureGroups(): LiveData<Resource<Manifest?>> {
        if (groups == null) {
            return pictureRepository.getGroups()
        }
        return liveData { emit(Resource.success(groups)) }
    }

    fun nextAlbum(): Album {
        currentGroupIndex += 1
        return getCurrentAlbum()
    }

    fun previousAlbum(): Album {
        currentGroupIndex -= 1
        return getCurrentAlbum()
    }

    fun hasMoreGroups() = currentGroupIndex !=  albums.lastIndex

    fun isFirstIndex() = currentGroupIndex == 0

    fun getPictureDetails(pictureContainer: PictureContainer): LiveData<Resource<PictureContainer?>> {
        if (pictureContainer.pictureDetails == null) {
            return pictureRepository.getPictureDetails(pictureContainer)
        }
        return liveData { emit(Resource.success(pictureContainer)) }
    }

    fun refreshGroups(manifest: Manifest?) {
        groups = manifest
        albums = mutableListOf()
        var firstPictureId: String? = null
        manifest?.let {
            for(group in it.groups){
                val pictureList = mutableListOf<PictureContainer>()
                for (pictureId in group){
                    if(firstPictureId == null){
                        firstPictureId = pictureId
                    }
                    pictureList.add(PictureContainer(pictureId))
                }
                albums.add(Album(pictureList))
            }
        }
    }

    fun getCurrentAlbum() = albums[currentGroupIndex]

}
