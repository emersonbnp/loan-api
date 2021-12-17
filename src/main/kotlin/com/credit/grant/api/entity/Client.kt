package com.credit.grant.api.entity

import com.credit.grant.api.response.ClientResponse
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@Entity
data class Client (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @get: NotBlank
    val name: String,

    @get: Positive
    val score: Int,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    val loans: List<Loan>
) {
    constructor(name: String, score: Int): this(id = null, name = name, score = score, loans = emptyList())
    fun toClientResponse() = ClientResponse(id = id!!, name = name, score = score)
}
