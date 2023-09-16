package com.vilinesoft.data.repository

import com.vilinesoft.data.db.DocumentDao
import com.vilinesoft.data.model.DocumentEntity
import com.vilinesoft.data.model.DocumentItemEntity
import com.vilinesoft.data.model.DocumentsDto
import com.vilinesoft.data.model.fromEntity
import com.vilinesoft.data.model.toEntity
import com.vilinesoft.data.network.remote.MainApi
import com.vilinesoft.data.handleResponse
import com.vilinesoft.domain.model.BaseError
import com.vilinesoft.domain.model.Document
import com.vilinesoft.domain.model.DocumentItem
import com.vilinesoft.domain.model.DocumentType
import com.vilinesoft.domain.model.Either
import com.vilinesoft.domain.model.Good
import com.vilinesoft.domain.model.GoodDto
import com.vilinesoft.domain.repository.MainRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class MainRepositoryImpl(
    private val api: MainApi,
    private val dao: DocumentDao
) : MainRepository {

    override suspend fun requestGood(
        barcode: String,
        docType: Int,
        storeCode: String?,
    ): GoodDto {
        return GoodDto(
            good = Good(
                123,
                "Водичка",
                99.99,
                "5449000044839",
                "142",
                1.0,
                "фыв",
                1,
                5,
                7
            ),
            status = 1
        )
    }

    override suspend fun fetchDocuments(): ImmutableList<Document> {
        return dao.getAllDocuments().map(DocumentEntity::fromEntity).toImmutableList()
    }

    override suspend fun fetchDocumentById(documentId: String): Document {
        return dao.getDocumentWithItems(documentId).fromEntity()
    }

    override suspend fun requestDocumentTypes(): List<DocumentType> {
        return listOf(
            DocumentType.PEREOB,
            DocumentType.PRIBUT,
            DocumentType.VIDAT,
            DocumentType.SPISAN,
            DocumentType.POVERN,
            DocumentType.PEREMIS,
            DocumentType.PEREOCIN,
        )
            //api.requestDocumentTypes()
    }

    override suspend fun deleteDocuments(ids: List<String>): Either<BaseError, Unit> {
        return handleResponse {
            dao.deleteDocuments(ids.toTypedArray())
        }
    }

    override suspend fun saveDocument(document: Document): Either<BaseError, Unit> {
        return handleResponse {
            dao.insertDocument(document.toEntity())
        }
    }

    override suspend fun sendDocuments(ids: List<String>): Either<BaseError, Unit> {
        return handleResponse {
            val docs = dao.getSelectedDocuments(createMultipleSelectionString(ids))
            val docItems = mutableListOf<DocumentItemEntity>()
            docs.forEach {
                docItems.addAll(dao.getSelectedDocumentItems(it.id))
            }
            api.sendDocuments(
                "application/json; charset=ISO-8859-1",
                DocumentsDto(docs, docItems)
            )
        }
    }

    private fun createMultipleSelectionString(ids: List<String>): String {
        var idsString = ""
        for (i in ids.indices) {
            idsString += "'" + ids[i] + "',"
        }
        idsString = StringBuilder(idsString).deleteCharAt(idsString.length - 1).toString()
        return idsString
    }

    override suspend fun getDocumentsCount(): Int {
        return dao.getDocumentsCount()
    }

    override suspend fun saveItem(item: DocumentItem) {
        dao.insertDocumentItem(item.toEntity())
    }

    override suspend fun fetchItemByBarcode(barcode: String, docId: String): DocumentItem? {
        return try {
            dao.getDocumentItemByBarcode(barcode, docId).fromEntity()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}