package com.mandarjoshi.picturelibrary.repo

import com.mandarjoshi.picturelibrary.model.PictureDetails
import com.mandarjoshi.picturelibrary.model.Manifest
import retrofit2.http.GET
import retrofit2.http.Path

interface PictureService {
    @GET("manifest")
    suspend fun getGroups(): Manifest

    @GET("image/{id}")
    suspend fun getPictureDetails(@Path("id") id: String): PictureDetails

}
