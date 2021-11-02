package com.example.workWithDB.controllers

import com.example.workWithDB.Service.PersonService
import com.example.workWithDB.models.Person
import com.example.workWithDB.repositories.PersonRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(
    private val personService: PersonService) {

    @GetMapping
    fun getAll():List<Person> =
        personService.getAll()

    @GetMapping("name/{name}")
    fun findByName(@PathVariable("name") name: String):List<Person> =
        personService.findByName(name)

    @GetMapping("lastName/{lastName}")
    fun findByLastName(@PathVariable lastName: String) =
        personService.findByLastName(lastName)

    @GetMapping("/id/{id}")
    fun findById(@PathVariable("id") id:Int): Person =
        personService.findById(id)

    @PostMapping
    fun addNewPerson(@RequestParam name: String, @RequestParam lastName: String){
        personService.addNewPerson(Person(name = name, lastName = lastName))
    }

    @PutMapping("/{id}")
    fun updatePerson(@PathVariable("id") id:Int, @RequestParam name: String, @RequestParam lastName: String){
        personService.updatePerson(id,Person(name = name, lastName = lastName))
    }

    @DeleteMapping("/{id}")
    fun deletePerson(@PathVariable("id") id:Int){
        personService.deleteById(id)
    }
}