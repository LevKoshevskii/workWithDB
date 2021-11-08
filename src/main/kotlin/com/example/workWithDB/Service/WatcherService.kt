package com.example.workWithDB.Service

import com.example.workWithDB.ActiveMQ.ActiveMQSender
import com.example.workWithDB.models.Person
import com.example.workWithDB.util.JsonReader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchKey
import java.nio.file.WatchService

@Service

class WatcherService(private val personService: PersonService,
                    private val activeMQSender: ActiveMQSender) {
    val log: Logger = LoggerFactory.getLogger("WatcherService")
    fun watchNewPersons(){
        val directoryPath = Paths.get("/Users/lev/Desktop/workWithDB/src/main/resources/newPersons")
        val watcher: WatchService = directoryPath.fileSystem.newWatchService()
        val key: WatchKey = directoryPath.register(watcher, StandardWatchEventKinds.ENTRY_CREATE)

        while (true) {
            for (event in key.pollEvents()){
                when (event.kind()) {
                    StandardWatchEventKinds.ENTRY_CREATE -> {
                        print(event.context())
                        val input = File("$directoryPath/${event.context()}")
                        val fileName:String = "${event.context()}"
                        val inputText = input.readText()
                        if (fileName.substring(fileName.lastIndexOf(".")+1) == "json") {
                            val jsonReader = JsonReader()
                            var persons: List<Person> = jsonReader.getPersons(inputText)
                            persons.forEach {
                                if (!personService.addNewPersonIfNotExist(it)) {
                                    log.info("ignoring Person{${it.name}, ${it.lastName}}")
                                }
                            }
                        }else{
                            activeMQSender.sendXmlToPersonsQueue(inputText)
                        }
                    }
                }
                key.reset()
            }
        }
    }
}