package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.GirlPhotoRetrieveDto
import com.mpi.kissing_company.entities.GirlPhoto
import com.mpi.kissing_company.repositories.GirlPhotoRepository
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.UserRepository
import com.mpi.kissing_company.utils.GirlPhotoUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
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

    @PostMapping("/api/girls_photos")
    fun uploadImage(auth: Authentication, @RequestParam("image") image: MultipartFile, @RequestParam("is_profile_photo") isProfilePhoto: Boolean = false): GirlPhotoRetrieveDto{
        val user = user_repository.findByUsername(auth.name).get()
        val girl_entity = girl_repository.findByUser(user).orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Looks like u are not a girl. So sorry") }

        val filename = image.originalFilename
        val filename_dot_index = filename?.indexOf(".")
        if (filename_dot_index == -1){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Filename must have file extension")
        }
        val uuid = UUID.randomUUID().toString()
        val new_filename = uuid + filename_dot_index?.let { filename?.substring(it) }
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
        var new_photo_dto = girlPhotoUtils.mapToRetrieveDto(new_photo)
        return new_photo_dto
    }

    @GetMapping("/api/girls_photos/download_image/{id}", produces = arrayOf(MediaType.IMAGE_JPEG_VALUE))
    fun downloadPhotoById(@PathVariable id: Long): ResponseEntity<Resource>{
        val image_db = repository.findById(id).orElseThrow{ ResponseStatusException(HttpStatus.NOT_FOUND, "Not found") }
        val inputStream = ByteArrayResource(
                Files.readAllBytes(
                    Paths.get(image_db?.getPhotoUri()
                )
            )
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentLength(inputStream.contentLength())
            .body(inputStream);
    }

    @GetMapping("/api/girls_photos/{girl_id}")
    fun getAllByGirl(@PathVariable girl_id: Long, @RequestParam("is_profile_photo") isProfilePhoto: Boolean? = null): List<GirlPhotoRetrieveDto>{
        val girl = girl_repository.findById(girl_id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found") }
        var girl_entity: List<GirlPhoto?>? = null
        if (isProfilePhoto == null){
            girl_entity = girl?.let { repository.findByGirlOrderByCreateDtDesc(it) }
        }
        else{
            girl_entity = girl?.let { repository.findByGirlAndIsProfilePhotoOrderByCreateDtDesc(it, isProfilePhoto) }
        }
        if (girl_entity != null) {
            return girl_entity.map { girlPhotoUtils.mapToRetrieveDto(it) }
        }
        return emptyList()
    }

    @DeleteMapping("/api/girls_photos/{id}")
    @Transactional
    fun deletePhotoById(auth: Authentication, @PathVariable id: Long){
        val photo = repository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found") }
        repository.deleteById(id)
        val file = File(photo?.getPhotoUri())
        file.delete()
    }

}