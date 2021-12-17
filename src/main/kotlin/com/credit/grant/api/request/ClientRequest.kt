package com.credit.grant.api.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class ClientRequest (

    @get: NotBlank
    val name: String,

    @get: Positive
    val score: Int
)
