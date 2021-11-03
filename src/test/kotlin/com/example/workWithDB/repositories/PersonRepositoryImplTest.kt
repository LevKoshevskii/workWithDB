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
        assertThat(person).isEqualTo(Person(3,"Лев","Толстой"))
    }

    @Test
    fun addNewPerson(){
        val newPerson: Person = Person(name = "Иван", lastName = "Иванов")
        personRepository.addNewPerson(newPerson)
        val newPersonId: Int = personRepository.findByName("Иван")[0].id
        val newPersonName: String = personRepository.getAll()[4].name
        val newPersonLastName: String = personRepository.getAll()[4].lastName

        assertThat(newPersonName).isEqualTo("Иван")
        assertThat(newPersonLastName).isEqualTo("Иванов")

        personRepository.deleteById(newPersonId)
    }

    @Test
    fun updatePerson(){
        val newPerson: Person = Person(name = "Иван", lastName = "Иванов")
        personRepository.addNewPerson(newPerson)

        val newPersonId: Int = personRepository.findByName("Иван")[0].id

        val updatedPerson: Person = Person(name = "Василий", lastName = "Васильев")
        personRepository.updatePerson(newPersonId,updatedPerson)

        val updatedPersonName: String = personRepository.findById(newPersonId)!!.name
        val updatedPersonLastName: String = personRepository.findById(newPersonId)!!.lastName

        assertThat(updatedPersonName).isEqualTo("Василий")
        assertThat(updatedPersonLastName).isEqualTo("Васильев")

        personRepository.deleteById(newPersonId)
    }

    @Test
    fun deleteById(){
        val newPerson: Person = Person(name = "Иван", lastName = "Иванов")
        personRepository.addNewPerson(newPerson)

        val newPersonId: Int = personRepository.findByName("Иван")[0].id

        personRepository.deleteById(newPersonId)
        val deletePerson: Person? = personRepository.findById(newPersonId) ?: null

        assertThat(deletePerson).isEqualTo(null)
    }
}