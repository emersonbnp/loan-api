package com.credit.grant.api.request

import com.credit.grant.api.enum.LoanStatus
import org.hibernate.annotations.Where
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

data class LoanRequest (
    val status: LoanStatus,

    @get: NotEmpty
    val description: String,

    @get: Positive
    val value: Double
)