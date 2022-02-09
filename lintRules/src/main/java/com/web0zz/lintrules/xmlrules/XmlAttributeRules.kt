package com.web0zz.lintrules.xmlrules

interface XmlAttributeRules {
    fun isAllowedAttributeUsage(
        attributeValue: String
    ): Boolean

    fun getMessage(): String
}
