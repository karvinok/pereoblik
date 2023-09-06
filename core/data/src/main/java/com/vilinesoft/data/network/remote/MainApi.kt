package com.vilinesoft.data.network.remote

import com.vilinesoft.data.BaseResponse
import com.vilinesoft.data.model.DocumentTypesDto
import com.vilinesoft.data.model.DocumentsDto
import com.vilinesoft.data.model.GoodsDto
import retrofit2.http.*

interface MainApi {
    @GET("/getgood")
    suspend fun requestGood(
        @Query("barcode") barcode: String?,
        @Query("docType") docType: Int,
        @Query("storecode") storecode: String?,
    ): GoodsDto

    @GET("/ords/inventory/v1/Docs")
    suspend fun requestDocuments(): DocumentsDto

    @POST("/writeDocs")
    suspend fun sendDocuments(
        @Header("Content-Type") contentType: String?,
        @Body docs: DocumentsDto?,
    ): BaseResponse

    @GET("/getType")
    suspend fun requestDocumentTypes(): DocumentTypesDto
}