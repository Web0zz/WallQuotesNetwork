package com.web0zz.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.web0zz.lintrules.importrules.ImportDetector.Companion.ISSUE_IMPORT_DETECTOR

@Suppress("UnstableApiUsage")
class MyIssueRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(
            ISSUE_IMPORT_DETECTOR
        )

    override val api: Int = CURRENT_API
}
