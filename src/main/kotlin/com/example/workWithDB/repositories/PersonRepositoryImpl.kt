package com.example.workWithDB.repositories

import com.example.workWithDB.models.Person
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class PersonRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate): PersonRepository {

    override fun getAll(): List<Person>{
        return jdbcTemplate.query("SELECT * FROM Person", ROW_MAPPER)
    }

    override fun findByName(name: String): List<Person>?{
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=:name", mapOf("name" to name), ROW_MAPPER)
    }

    override fun findByLastName(lastName: String): List<Person>?{
        return jdbcTemplate.query("SELECT * FROM Person WHERE lastname=:lastName",
            mapOf("lastName" to lastName), ROW_MAPPER)
    }

    override fun findById(id : Int): Person? {
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE id=:id",
            mapOf("id" to id), ROW_MAPPER)
    }

    override fun addNewPerson(person: Person){
        jdbcTemplate.update("INSERT INTO Person (name, lastname) VALUES(:name, :lastName)",
        mapOf("name" to person.name,"lastName" to person.lastName))
    }

    override fun updatePerson(id: Int,updatedPerson: Person){
        jdbcTemplate.update("UPDATE Person SET name = :name, lastName=:lastName WHERE  id=:id",
        mapOf("id" to id, "name" to updatedPerson.name, "lastName" to updatedPerson.lastName))
    }

    override fun deleteById(id: Int){
        jdbcTemplate.update("DELETE FROM Person WHERE id=:id", mapOf("id" to id))
    }

    private companion object {
        val ROW_MAPPER = RowMapper<Person>{ rs, _ ->
            Person(
                id = rs.getInt("id"),
                name = rs.getString("name"),
                lastName = rs.getString("lastName")
            )
        }
    }
}