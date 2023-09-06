package com.vilinesoft.domain.model


enum class DocumentType(val typeNumber: Int) {

    PEREOB(101),
    PRIBUT(102),
    VIDAT(103),
    SPISAN(104),
    POVERN(105),
    PEREMIS(106),
    PEREOCIN(107);

    fun toName() = when (this) {
        PEREOB -> "Переоблік"
        PRIBUT -> "Прибуткова"
        VIDAT -> "Видаткова"
        SPISAN -> "Списання"
        POVERN -> "Повернення"
        PEREMIS -> "Переміщення"
        PEREOCIN -> "Переоцінка"
    }

    override fun toString(): String {
        return toName()
    }

    companion object {
        fun valueOf(typeNumber: Int?) = when(typeNumber) {
            101 -> PEREOB
            102 -> PRIBUT
            103 -> VIDAT
            104 -> SPISAN
            105 -> POVERN
            106 -> PEREMIS
            107 -> PEREOCIN
            else -> PEREOB
        }
    }

}
