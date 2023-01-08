package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.GirlDto
import com.mpi.kissing_company.entities.InviteLinks
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.InviteLinksRepository
import com.mpi.kissing_company.repositories.RoleRepository
import com.mpi.kissing_company.repositories.UserRepository
import com.mpi.kissing_company.utils.GirlUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime


@RestController
internal class GirlController(private val repository: GirlRepository,
                              private val invite_repository: InviteLinksRepository,
                              private val user_repository: UserRepository,
                              private val role_repository: RoleRepository) {

    @Autowired
    private val girlUtils = GirlUtils()

    @GetMapping("/girls")
    fun all(): List<GirlDto> {
        val girl_entity = repository.findAll()
        return girl_entity.map { girlUtils.mapToDto(it) }
    }

    @GetMapping("/girls/{id}")
    fun one(@PathVariable id: Long): GirlDto {
        val girl_entity = repository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found") }
        return girlUtils.mapToDto(girl_entity)
    }

    @GetMapping("/girls/me")
    fun getGirlOfMyself(auth: Authentication): GirlDto {
        val user = user_repository.findByUsername(auth.name).get()
        val girl_entity = repository.findByUser(user).orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Looks like u are not a girl. So sorry") }
        return girlUtils.mapToDto(girl_entity)
    }

    @PostMapping("/girls/registration/{token}")
    @Transactional
    fun registrateGirl(auth: Authentication, @RequestBody newGirl: GirlDto, @PathVariable token: String): GirlDto {

        val user = user_repository.findByUsername(auth.name).get()

        if (repository.existsByUser(user)){
            throw ResponseStatusException(HttpStatus.CONFLICT, "Current user already a girl")
        }

        if (user.getRole()?.name == "ADMIN"){
            throw ResponseStatusException(HttpStatus.CONFLICT, "User with role \"ADMIN\" can't become girl. Based")
        }

        var invite_token: InviteLinks?
        try {
            invite_token = invite_repository.findByinviteToken(token)
        }
        catch(ex: EmptyResultDataAccessException){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Invite token not found")
        }
        val now = LocalDateTime.now()
        if (now.isAfter(invite_token.getexpirtyDt())){
            invite_repository.deleteByinviteToken(token)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invite link is expired")  // FIXME: don't always return message
        }
        invite_repository.deleteByinviteToken(token)

        newGirl.setUsername(user.getUsername())
        val role = role_repository.findByName("HOOKER")
        user.setRole(role)
        user_repository.save(user)

        var new_girl_id = repository.save(girlUtils.mapToEntity(newGirl)).getId()

        return girlUtils.mapToDto(new_girl_id?.let { repository.findById(it).get() })

    }

    @PutMapping("/girls/{id}")
    fun updateGirl(@RequestBody newGirl: GirlDto, @PathVariable id: Long): GirlDto {
        if (repository.existsById(id) == false){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found")
        }
        var current_girl = repository.findById(id).get()
//        current_girl.setUser(girlUtils.mapToEntity(newGirl).getUser())
        current_girl.setLocation(newGirl.getLocation())
        current_girl.setWeight(newGirl.getWeight())
        current_girl.setHeight(newGirl.getHeight())
        current_girl.setNation(newGirl.getNation())
        current_girl.setHair_color(newGirl.getHair_color())
        current_girl.setAge(newGirl.getAge())
        current_girl.setTelephone(newGirl.getTelephone())
        current_girl.setNikname(newGirl.getNikname())
        repository.save(current_girl)
        return girlUtils.mapToDto(current_girl)
    }

    @DeleteMapping("/girls/{id}")
    @Transactional
    fun deleteGirl(@PathVariable id: Long){
        if (repository.existsById(id) == false){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found")
        }
        repository.deleteById(id)
    }


}
