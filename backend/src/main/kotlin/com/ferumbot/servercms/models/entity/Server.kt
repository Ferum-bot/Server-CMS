package com.ferumbot.servercms.models.entity

import com.ferumbot.servercms.enums.ServerStatus
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Server(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @NotBlank(message = "Name can't be empty!")
    @Column(unique = true, nullable = false)
    var name: String? = null,

    @NotBlank(message = "Api Address can't be empty!")
    @Column(unique = true, nullable = false)
    var apiAddress: String? = null,

    @NotBlank(message = "Memory can't be empty!")
    var memory: String? = null,

    @NotBlank(message = "Type can't be empty!")
    var type: String? = null,

    var imageUrl: String? = null,

    var status: ServerStatus = ServerStatus.SERVER_DOWN,

    var createdDate: LocalDateTime = LocalDateTime.now(),

    var updatedDate: LocalDateTime = LocalDateTime.now(),
) {

    @PrePersist
    fun onSave() {
        createdDate = LocalDateTime.now()
        updatedDate = createdDate
    }

    @PreUpdate
    fun onUpdate() {
        updatedDate = LocalDateTime.now()
    }
}
