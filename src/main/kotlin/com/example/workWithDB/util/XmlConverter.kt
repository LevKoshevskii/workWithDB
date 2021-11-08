package com.example.workWithDB.util

import com.example.workWithDB.models.Person
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.dataformat.xml.XmlMapper


class XmlConverter {
    fun convertXmlToPersonList(xmlString: String): List<Person>{
        val xmlMapper = XmlMapper()
        return xmlMapper.readValue(xmlString, object : TypeReference<List<Person>>() {})
    }
}

