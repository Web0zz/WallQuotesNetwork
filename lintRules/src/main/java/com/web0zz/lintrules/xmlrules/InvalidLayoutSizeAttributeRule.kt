package com.web0zz.lintrules.xmlrules

class InvalidLayoutSizeAttributeRule : XmlAttributeRules {
    override fun isAllowedAttributeUsage(attributeValue: String) =
        isHardcodedDimen(attributeValue)

    override fun getMessage() = """
               Declared views in layout resource should not contain hardcoded dimension values.
               
               It makes harder to add new feature on later.
            """.trimIndent()

    private fun isHardcodedDimen(attributeValue: String) =
        ("([0-9]+)dp".toRegex().matchEntire(attributeValue)) != null
}
