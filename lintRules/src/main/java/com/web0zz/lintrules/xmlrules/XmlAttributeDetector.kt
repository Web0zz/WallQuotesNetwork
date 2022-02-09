package com.web0zz.lintrules.xmlrules

import com.android.SdkConstants
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr

@Suppress("UnstableApiUsage")
class XmlAttributeDetector : LayoutDetector() {
    private val rules = listOf(
        InvalidLayoutSizeAttributeRule()
    )

    override fun getApplicableAttributes(): Collection<String> {
        return listOf(
            SdkConstants.ATTR_LAYOUT_WIDTH,
            SdkConstants.ATTR_LAYOUT_HEIGHT
        )
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        rules.forEach { rule ->
            if (!rule.isAllowedAttributeUsage(attribute.value)) {
                context.report(
                    ISSUE_XML_ATTRIBUTE_DETECTOR,
                    context.getLocation(attribute),
                    rule.getMessage()
                )
            }
        }
    }

    companion object {
        private const val MESSAGE = "Lint detector for detecting invalid xml attribute"

        @JvmField
        val ISSUE_XML_ATTRIBUTE_DETECTOR = Issue.create(
            id = "XmlIssueDetector",
            briefDescription = MESSAGE,
            explanation = MESSAGE,
            category = Category.PRODUCTIVITY,
            priority = 5,
            severity = Severity.WARNING,
            implementation = Implementation(
                XmlAttributeDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}
