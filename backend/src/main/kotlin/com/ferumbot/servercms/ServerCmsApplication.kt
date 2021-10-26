package com.ferumbot.servercms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServerCmsApplication

fun main(args: Array<String>) {
	runApplication<ServerCmsApplication>(*args)
}
