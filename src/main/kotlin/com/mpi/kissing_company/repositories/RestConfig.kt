package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Defka
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.stereotype.Component
import java.awt.print.Book

import org.springframework.web.servlet.config.annotation.CorsRegistry

import org.springframework.data.rest.core.config.RepositoryRestConfiguration




@Component
public class RestConfig: RepositoryRestConfigurer {

    override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration?, cors: CorsRegistry?) {
        config?.exposeIdsFor(Defka::class.java)
    }

}