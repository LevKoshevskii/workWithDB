package com.example.workWithDB.repositories

import com.example.workWithDB.models.Person

interface PersonRepository {

    fun getAll(): List<Person>

    fun findByName(name: String): List<Person>?

    fun findByLastName(lastName: String): List<Person>?

    fun findById(id: Int): Person?

    fun addNewPerson(person: Person)

    fun updatePerson(id: Int,updatedPerson: Person)

    fun deleteById(id: Int)

}