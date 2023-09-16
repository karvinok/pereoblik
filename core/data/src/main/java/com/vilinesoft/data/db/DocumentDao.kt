package com.vilinesoft.data.db

import androidx.annotation.Keep
import androidx.room.*
import com.vilinesoft.data.model.DocumentEntity
import com.vilinesoft.data.model.DocumentItemEntity

@Dao
@Keep
interface DocumentDao {

    suspend fun getSelectedDocuments(idsString: String): List<DocumentEntity> {
        return getDocumentsByIds(idsString)
    }

    suspend fun getSelectedDocumentItems(docId: String): List<DocumentItemEntity> {
        return getDocumentItemsByDocId(docId)
    }

    @Transaction
    suspend fun deleteDocuments(ids: Array<String>) {
        deleteDocumentsByIds(ids)
        deleteDocumentsItemsByIds(ids)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(addUser: DocumentEntity)

    @Transaction
    suspend fun getDocumentWithItems(docId: String): DocumentEntity {
        val documentItems = getDocumentItemsByDocId(docId)
        return getDocumentById(docId).apply {
            items = documentItems.sortedBy { it.id }
        }
    }

    @Query("SELECT * FROM DocumentTable WHERE id=:id")
    suspend fun getDocumentById(id: String): DocumentEntity

    @Query("SELECT * FROM DocumentTable")
    suspend fun getAllDocuments(): List<DocumentEntity>

    @Query("SELECT COUNT(id) FROM DocumentTable")
    suspend fun getDocumentsCount(): Int

    @Query("DELETE FROM DocumentTable WHERE id IN (:ids)")
    suspend fun deleteDocumentsByIds(ids: Array<String>)

    @Query("DELETE FROM DocumentItemTable WHERE idDoc IN (:ids)")
    suspend fun deleteDocumentsItemsByIds(ids: Array<String>)

    @Query("SELECT * FROM DocumentTable WHERE id IN (:idsString)")
    suspend fun getDocumentsByIds(idsString: String): List<DocumentEntity>

    @Query("SELECT * FROM DocumentItemTable WHERE idDoc =:docId")
    suspend fun getDocumentItemsByDocId(docId: String): List<DocumentItemEntity>

    @Query("SELECT * FROM DocumentItemTable WHERE idDoc =:docId")
    suspend fun getDocumentItemByDocId(docId: String): DocumentItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocumentItem(item: DocumentItemEntity)

    @Query("SELECT * FROM DocumentItemTable WHERE barcode = :barcode AND idDoc = :idDoc LIMIT 1")
    suspend fun getDocumentItemByBarcode(barcode: String, idDoc: String): DocumentItemEntity
}