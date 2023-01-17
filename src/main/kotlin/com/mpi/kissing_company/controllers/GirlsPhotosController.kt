package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.GirlPhotoDto
import com.mpi.kissing_company.entities.GirlPhoto
import com.mpi.kissing_company.repositories.GirlPhotoRepository
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.UserRepository
import com.mpi.kissing_company.utils.GirlPhotoUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path
import java.util.UUID

@RestController
internal class GirlsPhotosController(private val repository: GirlPhotoRepository,
                                     private val girl_repository: GirlRepository,
                                     private val user_repository: UserRepository) {

    @Autowired
    private val env: Environment? = null
    @Autowired
    private val girlPhotoUtils = GirlPhotoUtils()

    @PostMapping("/girls_photos")
    fun uploadImage(auth: Authentication, @RequestParam("image") image: MultipartFile, @RequestParam("is_profile_photo") isProfilePhoto: Boolean = false): GirlPhotoDto{
        val user = user_repository.findByUsername(auth.name).get()
        val girl_entity = girl_repository.findByUser(user).orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Looks like u are not a girl. So sorry") }

        val filename = image.originalFilename
        val filename_dot_index = filename?.indexOf(".")
        if (filename_dot_index == -1){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Filename must have file extension")
        }
        val uuid = UUID.randomUUID().toString()
        val new_filename = uuid+".jpg" //filename_dot_index?.let { filename?.substring(it) }
        var path = env?.getProperty("static_path.girls_images")
        if (path == ""){
            path = System.getProperty("user.dir")+"\\girls_images\\"
        }
        val directory = File(path)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val new_photo = repository.save(GirlPhoto(path+new_filename, girl_entity, isProfilePhoto))
        Files.write(Path(path+new_filename), image.bytes)
        var new_photo_dto = girlPhotoUtils.mapToDto(new_photo)
        new_photo_dto.setPhotoUri("")
        return new_photo_dto
    }


}