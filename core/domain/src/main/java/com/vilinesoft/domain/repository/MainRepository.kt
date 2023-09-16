package com.vilinesoft.domain.repository

import com.vilinesoft.domain.model.BaseError
import com.vilinesoft.domain.model.DocumentType
import com.vilinesoft.domain.model.Either
import com.vilinesoft.domain.model.Document
import com.vilinesoft.domain.model.DocumentItem
import com.vilinesoft.domain.model.GoodDto
import kotlinx.collections.immutable.ImmutableList

interface MainRepository {
    suspend fun requestGood(barcode: String, docType: Int, storeCode: String?): GoodDto
    suspend fun fetchDocuments(): ImmutableList<Document>
    suspend fun fetchDocumentById(documentId: String): Document
    suspend fun sendDocuments(ids: List<String>): Either<BaseError, Unit>
    suspend fun requestDocumentTypes(): List<DocumentType>
    suspend fun saveDocument(document: Document): Either<BaseError, Unit>
    suspend fun deleteDocuments(ids: List<String>): Either<BaseError, Unit>
    suspend fun getDocumentsCount(): Int
    suspend fun saveItem(item: DocumentItem)
    suspend fun fetchItemByBarcode(barcode: String, docId: String): DocumentItem?
}