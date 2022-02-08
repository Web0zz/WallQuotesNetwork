package com.web0zz.lintrules.importrules

class InvalidDomainToPresentationDependencyRule : InvalidImportRules {

    override fun isAllowedImport(
        visitingPackage: String,
        visitingClassName: String,
        importStatement: String
    ): Boolean =
        !((isDomainPackage(visitingPackage)) && isPresentationPackageImport(importStatement))

    override fun getMessage(): String = "Domain class should not import from presentation package"

    private fun isDomainPackage(packageName: String) =
        packageName.contains(".domain.") || packageName.endsWith("domain")

    private fun isPresentationPackageImport(importStatement: String) =
        importStatement.contains(".presentation.") || importStatement.endsWith("presentation")
}
