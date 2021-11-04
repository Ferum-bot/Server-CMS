package com.ferumbot.servercms.services

import com.ferumbot.servercms.models.entity.Server
import org.springframework.data.domain.Pageable

interface ServerService {

    fun create(server: Server): Server

    fun getAll(serverCount: Int, pageNumber: Int): Collection<Server>

    fun get(name: String): Server

    fun update(server: Server): Server

    fun delete(name: String): Boolean

    fun ping(apiAddress: String): Server
}