package com.ferumbot.servercms.repositories

import com.ferumbot.servercms.models.entity.Server
import org.springframework.data.jpa.repository.JpaRepository

interface ServerRepository: JpaRepository<Server, Long> {

    fun findByName(name: String): Server

    fun findByApiAddress(apiAddress: String): Server

    fun deleteByName(name: String)
}