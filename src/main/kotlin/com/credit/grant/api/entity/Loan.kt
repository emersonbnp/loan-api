package com.credit.grant.api.entity

import com.credit.grant.api.enum.LoanStatus
import com.credit.grant.api.response.LoanResponse
import org.hibernate.annotations.Where
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@Entity
@Where(clause = "DELETED = false")
data class Loan (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Client::class)
    val client: Client,

    val status: LoanStatus,

    @get: NotBlank
    val description: String,

    @get: Positive
    val value: Double,

    val deleted: Boolean = false
) {
    fun toLoanResponse() = LoanResponse(id = id!!, status = status, description = description, value = value)
}