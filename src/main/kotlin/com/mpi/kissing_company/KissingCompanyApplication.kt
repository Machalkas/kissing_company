package com.mpi.kissing_company

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories("com.mpi.kissing_company.*")
//@ComponentScan(basePackages = { "com.mpi.kissing_company.*" })
@EntityScan("com.mpi.kissing_company.*")
@SpringBootApplication
class KissingCompanyApplication

fun main(args: Array<String>) {
	runApplication<KissingCompanyApplication>(*args)
}
