package com.example.workWithDB.Service

import com.example.workWithDB.models.Person

interface PersonService {
    fun getAll(): List<Person>

    fun findByName(name: String): List<Person>

    fun findByLastName(lastName: String): List<Person>

    fun findById(id: Int): Person

    fun addNewPerson(person: Person)

    fun updatePerson(id: Int,updatedPerson: Person)

    fun deleteById(id: Int)

    fun addNewPersonIfNotExist(person: Person): Boolean
}