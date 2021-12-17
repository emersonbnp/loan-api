package com.credit.grant.api.response

import com.credit.grant.api.enum.LoanStatus
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class LoanResponse (
    val id: Long,

    val status: LoanStatus,

    val description: String,

    val value: Double
)