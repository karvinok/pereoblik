package com.vilinesoft.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.vilinesoft.domain.model.Document
import com.vilinesoft.domain.model.DocumentType

@Keep
@Entity(tableName = "DocumentTable")
class DocumentEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "",
    @ColumnInfo(name = "numberDoc")
    var numberDoc: String? = "",
    @ColumnInfo(name = "dateDoc")
    var dateDoc: String? = null,
    @ColumnInfo(name = "commDoc")
    var commDoc: String? = null,
    @ColumnInfo(name = "docType")
    var docType: Int? = null,
    @ColumnInfo(name = "is1C")
    var is1C: Int? = null,
    @ColumnInfo(name = "isChanged")
    var isChanged: Int? = null,
    @ColumnInfo(name = "storeCode")
    var storeCode: String? = null,
) {
    @Ignore
    var items: List<DocumentItemEntity>? = null

    @Ignore
    var isSelected: Boolean = false

    fun setIs1C(is1C: Int?) {
        this.is1C = is1C
    }

    fun setIsChanged(is1C: Int?) {
        this.is1C = is1C
    }
}

fun DocumentEntity.fromEntity() = Document(
    id, numberDoc, dateDoc, commDoc, DocumentType.valueOf(docType), is1C, isChanged, storeCode
)

fun Document.toEntity() = DocumentEntity(
    id, numberDoc, dateDoc, commDoc, docType?.typeNumber, is1C, isChanged, storeCode
)