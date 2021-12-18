package com.credit.grant.api.entity

import com.credit.grant.api.response.ClientResponse
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@Entity
class Client (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @get: NotNull
    val name: String = "",

    @get: PositiveOrZero
    @set: Positive
    var score: Int = 0
) {
    fun toClientResponse() = ClientResponse(id = id, name = name, score = score)
}
