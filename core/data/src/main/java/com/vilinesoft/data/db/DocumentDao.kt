package com.vilinesoft.data.db

import androidx.annotation.Keep
import androidx.room.*
import com.vilinesoft.data.model.DocumentEntity
import com.vilinesoft.data.model.DocumentItemEntity

@Dao
@Keep
interface DocumentDao {

    fun getSelectedDocuments(idsString: String): List<DocumentEntity> {
        return querySelectedDocuments(idsString)
    }

    fun getSelectedDocumentItems(docId: String): List<DocumentItemEntity> {
        return queryDocumentItems(docId)
    }

    fun deleteDocuments(ids: Array<String>) {
        queryDeleteDocumentsByIds(ids)
        queryDeleteDocumentsItemsByIds(ids)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDocument(addUser: DocumentEntity)

    @Query("SELECT * FROM DocumentTable WHERE id=:id")
    fun getDocument(id: String): DocumentEntity

    @Query("SELECT * FROM DocumentTable")
    fun getAllDocuments(): List<DocumentEntity>

    @Query("SELECT COUNT(id) FROM DocumentTable")
    fun getDocumentsCount(): Int

    @Query("DELETE FROM DocumentTable WHERE id IN (:ids)")
    fun queryDeleteDocumentsByIds(ids: Array<String>)

    @Query("DELETE FROM DocumentItemTable WHERE idDoc IN (:ids)")
    fun queryDeleteDocumentsItemsByIds(ids: Array<String>)

    @Query("SELECT * FROM DocumentTable WHERE id IN (:idsString)")
    fun querySelectedDocuments(idsString: String): List<DocumentEntity>

    @Query("SELECT * FROM DocumentItemTable WHERE idDoc =:id")
    fun queryDocumentItems(id: String): List<DocumentItemEntity>

    @Query("SELECT * FROM DocumentItemTable WHERE idDoc =:id")
    fun queryDocumentItem(id: String): DocumentItemEntity

}