package com.vilinesoft.domain.repository

import com.vilinesoft.domain.model.BaseError
import com.vilinesoft.domain.model.DocumentType
import com.vilinesoft.domain.model.Either
import com.vilinesoft.domain.model.Document
import com.vilinesoft.domain.model.Good

interface MainRepository {
    suspend fun requestGood(barcode: String, docType: Int, storeCode: String?): Either<BaseError, Good>
    suspend fun fetchDocuments(): List<Document>
    suspend fun sendDocuments(ids: List<String>): Either<BaseError, Unit>
    suspend fun requestDocumentTypes(): List<DocumentType>
    suspend fun saveDocument(document: Document): Either<BaseError, Unit>
    suspend fun deleteDocuments(ids: List<String>): Either<BaseError, Unit>
    suspend fun getDocumentsCount(): Int
}