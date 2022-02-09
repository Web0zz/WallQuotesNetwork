package com.web0zz.lintRules.xmlrules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.web0zz.lintrules.xmlrules.XmlAttributeDetector
import org.junit.Test

@Suppress("UnstableApiUsage")
class InvalidSizeDimensionAttributeTest {
    @Test
    fun `when a layout view has hardcoded dimension on layout size`() {
        TestLintTask.lint().files(
            LintDetectorTest.xml(
                "/res/layout/test.xml",
                """
                    <?xml version="1.0" encoding="utf-8"?>
                    <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                        android:layout_width="match_parent"
                        android:layout_height="145dp" />
                """.trimIndent()
            ).indented()
        ).issues(XmlAttributeDetector.ISSUE_XML_ATTRIBUTE_DETECTOR)
            .allowMissingSdk(true)
            .run()
            .expectWarningCount(1)
    }
}
