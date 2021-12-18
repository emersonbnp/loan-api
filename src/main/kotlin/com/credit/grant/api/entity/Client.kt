package com.credit.grant.api.entity

import com.credit.grant.api.response.ClientResponse
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@Entity
class Client (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @get: NotBlank
    val name: String,

    @get: Positive
    var score: Int
) {
    constructor(name: String, score: Int): this(id = null, name = name, score = score)
    constructor(id: Long): this(id = id, name = "", score = 0)
    fun toClientResponse() = ClientResponse(id = id!!, name = name, score = score)
}
