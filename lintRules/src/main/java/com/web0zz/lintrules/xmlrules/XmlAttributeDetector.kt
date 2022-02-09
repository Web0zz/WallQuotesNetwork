package com.web0zz.lintrules.xmlrules

import com.android.SdkConstants
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.TextFormat
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr

@Suppress("UnstableApiUsage")
class XmlAttributeDetector : LayoutDetector() {
    // TODO add more attribute rules

    override fun getApplicableAttributes(): Collection<String> {
        return listOf(
            SdkConstants.ATTR_LAYOUT_WIDTH,
            SdkConstants.ATTR_LAYOUT_HEIGHT
        )
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val matchResult = "([0-9]+)dp".toRegex().matchEntire(attribute.value)

        if (matchResult != null) {
            context.report(
                ISSUE_XML_ATTRIBUTE_DETECTOR,
                context.getLocation(attribute),
                ISSUE_XML_ATTRIBUTE_DETECTOR.getExplanation(TextFormat.TEXT)
            )
        }
    }

    companion object {
        @JvmField
        val ISSUE_XML_ATTRIBUTE_DETECTOR = Issue.create(
            id = "XmlIssue",
            briefDescription = "Lint detector for unrecommended xml attributes declaration",
            explanation = """
               Declared views in layout resource should not contain hardcoded dimension values.
               
               It makes harder to add new feature on later.
            """,
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
