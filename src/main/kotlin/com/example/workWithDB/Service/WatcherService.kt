package com.example.workWithDB.Service

import com.example.workWithDB.models.Person
import com.example.workWithDB.repositories.PersonRepository
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
class WatcherService(private val personRepository: PersonRepository) {
    val log: Logger = LoggerFactory.getLogger("WatcherService")
    fun watchNewPersons(){
        val directoryPath = Paths.get("/Users/lev/Desktop/workWithDB/src/main/resources/json")
        val watcher: WatchService = directoryPath.fileSystem.newWatchService()
        val key: WatchKey = directoryPath.register(watcher, StandardWatchEventKinds.ENTRY_CREATE)

        while (true) {
            for (event in key.pollEvents()){
                when (event.kind()) {
                    StandardWatchEventKinds.ENTRY_CREATE -> {
                        val input = File("$directoryPath/${event.context()}")
                        val json = input.readText()
                        val jsonReader = JsonReader()
                        var isExist: Boolean = false
                        var persons: List<Person> = jsonReader.getPersons(json)
                        for (person in persons){
                            val existingPerson: List<Person> = personRepository.findByName(person.name)
                            for (existPerson in existingPerson){
                                if (person.equals(existPerson)){
                                        log.info("ignoring Person{${person.name}, ${person.lastName}}")
                                    isExist = true
                                    break
                                }
                            }
                            if (!isExist){
                                personRepository.addNewPerson(person)
                            }
                        }
                    }
                }
                key.reset()
            }
        }
    }
}