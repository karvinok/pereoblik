package com.vilinesoft.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vilinesoft.domain.model.DocumentItem
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "DocumentItemTable")
data class DocumentItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "idDoc")
    var idDoc: String? = null,
    @ColumnInfo(name = "codeGoods1C")
    var codeGoods1C: String? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "barcode")
    var barcode: String? = null,
    @ColumnInfo(name = "price")
    var price: Double? = null,
    @ColumnInfo(name = "qtyBalance")
    var qtyBalance: Double? = null,
    @ColumnInfo(name = "qtyFact")
    var qtyFact: Double? = null,
    @ColumnInfo(name = "unit")
    var unit: String? = null,
    @ColumnInfo(name = "isNew")
    var isNew: Boolean = false,
) : Parcelable

fun DocumentItemEntity.fromEntity() = DocumentItem(
    id = id,
    idDoc = idDoc,
    codeGoods1C = codeGoods1C,
    name = name,
    barcode = barcode,
    price = price,
    qtyBalance = qtyBalance,
    qtyFact = qtyFact,
    unit = unit,
    isNew = isNew
)

fun DocumentItem.toEntity() = DocumentItemEntity(
    id = id,
    idDoc = idDoc,
    codeGoods1C = codeGoods1C,
    name = name,
    barcode = barcode,
    price = price,
    qtyBalance = qtyBalance,
    qtyFact = qtyFact,
    unit = unit,
    isNew = isNew
)