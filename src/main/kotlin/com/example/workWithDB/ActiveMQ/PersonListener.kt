package com.example.workWithDB.ActiveMQ

import com.example.workWithDB.Service.PersonService
import com.example.workWithDB.models.Person
import com.example.workWithDB.util.XmlConverter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

@Component
class PersonListener(private val personService:PersonService) {

    val log: Logger = LoggerFactory.getLogger("JmsListener")
    private val xmlConverter: XmlConverter = XmlConverter()

    @JmsListener(destination = "persons")
    fun addNewListenedPerson(listenedXml: String){
        val persons: List<Person> = xmlConverter.convertXmlToPersonList(listenedXml)

        persons.forEach{
            if (!personService.addNewPersonIfNotExist(it)){
                log.info("ignoring Person{${it.name}, ${it.lastName}}")
            }
        }
    }
}
