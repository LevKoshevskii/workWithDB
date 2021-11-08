package com.example.workWithDB.Service

import com.example.workWithDB.models.Person
import com.example.workWithDB.repositories.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonServiceImpl(private val personRepository: PersonRepository): PersonService {
    override fun getAll(): List<Person> = personRepository.getAll()

    override fun findByName(name: String): List<Person> {
        val result:List<Person>  = personRepository.findByName(name)
        if (result.isNotEmpty()) return result else throw Exception("Нет людей с таким именем")
    }

    override fun findByLastName(lastName: String): List<Person> {
        val result:List<Person>  = personRepository.findByLastName(lastName)
        if (result.isNotEmpty()) return result else throw Exception("Нет людей с такой фамилией")
    }

    override fun findById(id: Int): Person =
        personRepository.findById(id) ?: throw Exception("Нет человека с таким id")

    override fun addNewPerson(person: Person) {
        personRepository.addNewPerson(person)
    }

    override fun updatePerson(id: Int, updatedPerson: Person) {
        personRepository.updatePerson(id,updatedPerson)
    }

    override fun deleteById(id: Int) {
        personRepository.deleteById(id)
    }

    override fun addNewPersonIfNotExist(newPerson: Person): Boolean {
        val existingPerson: List<Person> = personRepository.findByName(newPerson.name)
        existingPerson.forEach{
            if (it.equals(newPerson)){
                return false
            }
        }
        personRepository.addNewPerson(newPerson)
        return true
    }
}