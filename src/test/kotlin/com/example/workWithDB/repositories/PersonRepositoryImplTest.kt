package com.example.workWithDB.repositories

import com.example.workWithDB.models.Person
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class PersonRepositoryImplTest {

    private val personRepository: PersonRepository
    @Autowired
    constructor(personRepository: PersonRepository){
        this.personRepository = personRepository
    }

    @Test
    fun getAll(){
        val people: List<Person> = personRepository.getAll()

        val expectedResult = listOf<Person>(
            Person(1,"Лев","Кошевский"),
            Person(2,"Вячеслав","Кошевский"),
            Person(3,"Лев","Толстой"),
            Person(4,"Борис","Щукин"),
        )
        assertThat(people).isEqualTo(expectedResult)
    }

    @Test
    fun findByName() {
        val people: List<Person> = personRepository.findByName("Лев")
        val expectedResult = listOf<Person>(
            Person(1,"Лев","Кошевский"),
            Person(3,"Лев","Толстой")
        )
        assertThat(people).isEqualTo(expectedResult)
    }

    @Test
    fun findByLastName(){
        val people: List<Person> = personRepository.findByLastName("Кошевский")
        val expectedResult = listOf<Person>(
            Person(1,"Лев","Кошевский"),
            Person(2,"Вячеслав","Кошевский")
        )
        assertThat(people).isEqualTo(expectedResult)
    }

    @Test
    fun findById() {
        val person: Person? = personRepository.findById(3)

        assertThat(person?.id).isEqualTo(3)
    }

    @Test
    fun addNewPerson(){
        val newPerson: Person = Person(name = "Иван", lastName = "Иванов")
        personRepository.addNewPerson(newPerson)
        val newPersonName: String = personRepository.getAll()[4].name
        val newPersonLastName: String = personRepository.getAll()[4].lastName

        assertThat(newPersonName).isEqualTo("Иван")
        assertThat(newPersonLastName).isEqualTo("Иванов")
    }

    @Test
    fun updatePerson(){
        val updatedPerson: Person = Person(name = "Василий", lastName = "Васильев")
        personRepository.updatePerson(4,updatedPerson)
        val updatedPersonName: String = personRepository.findById(4)!!.name
        val updatedPersonLastName: String = personRepository.findById(4)!!.lastName

        assertThat(updatedPersonName).isEqualTo("Василий")
        assertThat(updatedPersonLastName).isEqualTo("Васильев")
    }

    @Test
    fun deleteById(){
        personRepository.deleteById(4)
        val deletePerson: Person? = personRepository.findById(4) ?: null

        assertThat(deletePerson).isEqualTo(null)
    }
}