package com.ferumbot.servercms.services.impl

import com.ferumbot.servercms.enums.ServerStatus
import com.ferumbot.servercms.models.entity.Server
import com.ferumbot.servercms.repositories.ServerRepository
import com.ferumbot.servercms.services.ServerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.InetAddress
import javax.transaction.Transactional
import kotlin.math.PI

@Service
@Transactional
class ServerServiceImpl @Autowired constructor(
    private val repository: ServerRepository
): ServerService {

    companion object {

        private const val PING_TIME_OUT = 10000
    }

    override fun create(server: Server): Server {
        server.imageUrl = getServerImageUrl()
        return repository.save(server)
    }

    override fun getAll(serverCount: Int, pageNumber: Int): Collection<Server> {
        val page = Pageable.ofSize(serverCount).withPage(pageNumber)
        return repository.findAll(page).toList()
    }

    override fun get(name: String): Server {
        return repository.findByName(name)
    }

    override fun update(server: Server): Server {
        return repository.save(server)
    }

    override fun delete(name: String): Boolean {
        repository.deleteByName(name)
        return true
    }

    override fun ping(apiAddress: String): Server {
        val server = repository.findByApiAddress(apiAddress)
        val inetAddress = InetAddress.getByName(apiAddress)
        if (inetAddress.isReachable(PING_TIME_OUT)) {
            server.status = ServerStatus.SERVER_UP
        } else {
            server.status = ServerStatus.SERVER_DOWN
        }
        return repository.save(server)
    }

    private fun getServerImageUrl(): String {
        val imageNames = listOf("server1.png", "server2.png", "server3.png", "server4.png")
        val randomName = imageNames.random()
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/${randomName}").toUriString()
    }
}