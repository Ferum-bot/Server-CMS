package com.ferumbot.servercms

import com.ferumbot.servercms.configurations.CorsConfig
import com.ferumbot.servercms.controllers.ServerController
import com.ferumbot.servercms.services.ServerService
import com.ferumbot.servercms.services.impl.ServerServiceImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackageClasses = [
	ServerController::class,
	ServerService::class,
	ServerServiceImpl::class,
	CorsConfig::class,
])
@SpringBootApplication
class ServerCmsApplication

fun main(args: Array<String>) {
	runApplication<ServerCmsApplication>(*args)
}
