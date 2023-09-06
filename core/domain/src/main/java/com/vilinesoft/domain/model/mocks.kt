package com.vilinesoft.domain.model

fun mockedDocuments(
): MutableList<Document> {
    val mockDocumentItem1 = DocumentItem(
        id = "item1",
        name = "Item 1",
        barcode = "12345",
        price = 10.0,
        qtyBalance = 100.0,
        qtyFact = 50.0,
        unit = "pcs",
        isNew = true
    )
    val mockDocumentItem2 = DocumentItem(
        id = "item2",
        name = "Item 2",
        barcode = "67890",
        price = 20.0,
        qtyBalance = 200.0,
        qtyFact = 75.0,
        unit = "pcs",
        isNew = false
    )

    return mutableListOf(
        Document(
            id = "doc1",
            numberDoc = "No12312345",
            dateDoc = "Date",
            commDoc = "comment",
            docType = DocumentType.PEREOB,
            is1C = 1,
            isChanged = 0,
            storeCode = "STORE1",
            items = listOf(mockDocumentItem1, mockDocumentItem2)
        ),
        Document(
            id = "doc2",
            numberDoc = "No22312345",
            dateDoc = "Date",
            commDoc = "comment2",
            docType = DocumentType.POVERN,
            is1C = 1,
            isChanged = 0,
            storeCode = "STORE1",
            items = listOf(mockDocumentItem1, mockDocumentItem2)
        )
    )
}