package com.ferumbot.servercms.controllers

import com.ferumbot.servercms.enums.ServerStatus
import com.ferumbot.servercms.models.entity.Server
import com.ferumbot.servercms.models.response.ServerResponse
import com.ferumbot.servercms.services.ServerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.*
import java.nio.file.Files
import java.nio.file.Paths
import javax.validation.Valid

@RestController
@RequestMapping("/server")
class ServerController @Autowired constructor(
    private val service: ServerService
) {

    @GetMapping("/all")
    fun getServers(
        @RequestParam(name = "page_count", required = true)
        pageCount: Int,

        @RequestParam(name = "page_number", required = true)
        pageNumber: Int,
    ): ResponseEntity<ServerResponse<*>> {
        val allServers = service.getAll(pageCount, pageNumber)
        val response = ServerResponse.of(allServers)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/ping")
    fun pingServer(
        @RequestParam(name = "server_address", required = true)
        serverAddress: String,
    ): ResponseEntity<ServerResponse<*>> {
        val result = service.ping(serverAddress)
        val message = if (result.status == ServerStatus.SERVER_UP) {
            "Ping success"
        } else {
            "Ping failure"
        }
        val response = ServerResponse(message = message, data = result)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/save")
    fun saveServer(
        @Valid
        @RequestBody
        server: Server,
    ): ResponseEntity<ServerResponse<*>> {
        val result = service.create(server)
        val response = ServerResponse(
            status = HttpStatus.CREATED,
            statusCode = HttpStatus.CREATED.value(),
            data = result,
            message = "Server created!"
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/get/name")
    fun getServiceByName(
        @RequestParam(name = "name", required = true)
        name: String,
    ): ResponseEntity<ServerResponse<*>> {
        val result = service.get(name)
        val response = ServerResponse.of(result)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/update")
    fun updateService(
        @Valid
        @RequestBody
        server: Server,
    ): ResponseEntity<ServerResponse<*>> {
        val result = service.update(server)
        val response = ServerResponse(
            message = "Server Updated",
            data = result
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/delete/name")
    fun deleteServiceByName(
        @RequestParam(name = "name", required = true)
        name: String,
    ): ResponseEntity<ServerResponse<*>> {
        service.delete(name)
        val response = ServerResponse(
            message = "Server deleted",
            data = name,
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping(path = ["/image/{fileName}"], produces = [MimeTypeUtils.IMAGE_PNG_VALUE])
    fun getServerImage(
        @PathVariable(name = "fileName")
        fileName: String,
    ): ByteArray {
        val path = Paths.get(System.getProperty("user.home") + "/Documents/TestImages/" + fileName)
        return Files.readAllBytes(path)
    }
}