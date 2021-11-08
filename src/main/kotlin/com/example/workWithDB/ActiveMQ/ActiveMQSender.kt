package com.example.workWithDB.ActiveMQ

import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

@Component
class ActiveMQSender(private val jmsTemplate: JmsTemplate) {

    fun sendXmlToPersonsQueue(xmlToSend: String){
        jmsTemplate.convertAndSend("persons",xmlToSend)
    }
}