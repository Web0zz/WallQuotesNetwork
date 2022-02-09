package com.web0zz.lintRules.importRules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.web0zz.lintrules.importrules.ImportDetector
import org.junit.Test

@Suppress("UnstableApiUsage")
class InvalidDomainToDataDependencyRuleTest {
    @Test
    fun `when a domain class imports a class from data package then expect a warning`() {
        TestLintTask.lint().files(
            LintDetectorTest.java(
                "src/test/kotlin/testcase/domain/SomeDomainClass.java",
                """
                    package testcase.domain;
                    
                    import testcase.data.SomeDataClass;

                    public class SomeDomainClass {
                    
                    }
                """
            ).indented()
        ).issues(ImportDetector.ISSUE_IMPORT_DETECTOR)
            .allowMissingSdk(true)
            .allowCompilationErrors(true)
            .run()
            .expect(
                """
                    src/test/kotlin/testcase/domain/SomeDomainClass.java:3: Error: Domain class should not import from data package [IncorrectImportDetector]
                    import testcase.data.SomeDataClass;
                           ~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    1 errors, 0 warnings
                """
            )
    }
}
