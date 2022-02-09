package com.web0zz.lintrules.importrules

class InvalidDomainToDataDependencyRule : InvalidImportRules {
    override fun isAllowedImport(
        visitingPackage: String,
        visitingClassName: String,
        importStatement: String,
    ): Boolean = !((isDomainPackage(visitingPackage)) && isDataPackage(importStatement))

    override fun getMessage(): String = "Domain class should not import from data package"

    private fun isDomainPackage(packageName: String) =
        packageName.contains(".domain.") || packageName.endsWith("domain")

    private fun isDataPackage(importStatement: String) =
        importStatement.contains(".data.") || importStatement.endsWith("data")
}
