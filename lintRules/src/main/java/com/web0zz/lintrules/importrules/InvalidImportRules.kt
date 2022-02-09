package com.web0zz.lintrules.importrules

interface InvalidImportRules {
    fun isAllowedImport(
        visitingPackage: String,
        visitingClassName: String,
        importStatement: String,
    ): Boolean

    fun getMessage(): String
}
