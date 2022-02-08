package com.web0zz.lintrules.importrules

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UImportStatement
import org.jetbrains.uast.getContainingUFile

@Suppress("UnstableApiUsage")
class ImportDetector : Detector(), Detector.UastScanner {
    val rules = listOf(
        InvalidDomainToDataDependencyRule(),
        InvalidDomainToPresentationDependencyRule()
    )

    override fun getApplicableUastTypes() = listOf(UImportStatement::class.java)

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitImportStatement(node: UImportStatement) {
                node.importReference?.let { import ->
                    rules.forEach { rule ->
                        val visitingPackageName = import.getContainingUFile()?.packageName
                        val visitingClassName = context.file.nameWithoutExtension
                        val importedClass = import.asRenderString()

                        visitingPackageName?.let {
                            if (!rule.isAllowedImport(
                                    visitingPackageName,
                                    visitingClassName,
                                    importedClass
                                )
                            ) {
                                context.report(
                                    ISSUE_IMPORT_DETECTOR,
                                    node,
                                    context.getLocation(import),
                                    rule.getMessage()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val MESSAGE = "Lint detector for detecting invalid imports"

        val ISSUE_IMPORT_DETECTOR = Issue.create(
            id = "IncorrectImportDetector",
            briefDescription = MESSAGE,
            explanation = MESSAGE,
            category = Category.CORRECTNESS,
            priority = 9,
            severity = Severity.ERROR,
            implementation = Implementation(
                ImportDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}
