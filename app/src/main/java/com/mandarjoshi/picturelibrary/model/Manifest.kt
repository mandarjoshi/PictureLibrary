package com.mandarjoshi.picturelibrary.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Manifest(
    @Json(name = "manifest") var groups: List<List<String>>
)

@JsonClass(generateAdapter = true)
data class PictureDetails(
    @Json(name = "name") var name: String,
    @Json(name = "url") var url: String,
    @Json(name = "type") var type: String,
    @Json(name = "width") var width: Int,
    @Json(name = "height") var height: String
)

data class PictureContainer(var pictureId: String, var pictureDetails: PictureDetails? = null)

data class Album(var pictureContainers: List<PictureContainer>){
    fun allPictureDetailsLoaded(): Boolean {
        for (pictureContainer in pictureContainers){
            if(pictureContainer.pictureDetails == null){
                return false
            }
        }
        return true
    }
}
