package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.Role
import com.mpi.kissing_company.exceptions.RoleNotFoundException
import com.mpi.kissing_company.repositories.RoleRepository
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.function.Function
import java.util.function.Supplier


@RestController
internal class RoleController(private val repository: RoleRepository) {

    @GetMapping("/roles")
    fun all(): List<Role?> {
        return repository.findAll()
    }

    @PostMapping("/roles")
    fun newRole(@RequestBody newRole: Role): Role{
        newRole.name = newRole.name.uppercase()
        return repository.save(newRole)
    }

    @GetMapping("/roles/{id}")
    fun one(@PathVariable id: Long): Role? {
        return repository.findById(id)
            .orElseThrow(Supplier<RuntimeException> { RoleNotFoundException(id) })
    }

    @PostMapping("/roles/{id}")  // shuld be Put, but Put didnt work
    fun replaceRole(@RequestBody newRole: Role, @PathVariable id: Long): Optional<Role>? {
        println(id)
        return repository.findById(id)
            .map<Role>(Function { role: Role ->
                role.id = id
                role.name = newRole.name
                repository.save(role)} as (Role?) -> Role)
    }

    //    @DeleteMapping("/roles/{id}")
    @RequestMapping(value= ["/empdelete/{id}"], method= [RequestMethod.DELETE])
    fun deleteRolename(@PathVariable id: Long){
        repository.deleteById(id)
    }

}
