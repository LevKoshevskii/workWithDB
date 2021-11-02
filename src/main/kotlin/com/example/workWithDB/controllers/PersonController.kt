package com.example.workWithDB.controllers

import com.example.workWithDB.models.Person
import com.example.workWithDB.repositories.PersonRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(
    private val personRepository: PersonRepository) {

    @GetMapping
    fun getAll():List<Person> =
        personRepository.getAll()

    @GetMapping("name/{name}")
    fun findByName(@PathVariable("name") name: String):List<Person>? =
        personRepository.findByName(name)

    @GetMapping("lastName/{lastName}")
    fun findByLastName(@PathVariable lastName: String) =
        personRepository.findByLastName(lastName)

    @GetMapping("/id/{id}")
    fun findById(@PathVariable("id") id:Int): Person? =
        personRepository.findById(id)

    @PostMapping
    fun addNewPerson(@RequestParam name: String, @RequestParam lastName: String){
        personRepository.addNewPerson(Person(name = name, lastName = lastName))
    }

    @PutMapping("/{id}")
    fun addNewPerson(@PathVariable("id") id:Int, @RequestParam name: String, @RequestParam lastName: String){
        personRepository.updatePerson(id,Person(name = name, lastName = lastName))
    }

    @DeleteMapping("/{id}")
    fun deletePerson(@PathVariable("id") id:Int){
        personRepository.deleteById(id)
    }
}