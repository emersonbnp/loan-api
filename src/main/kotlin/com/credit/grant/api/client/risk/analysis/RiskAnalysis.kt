package com.credit.grant.api.client.risk.analysis

enum class RiskEnum {
    LOW,
    MEDIUM,
    HIGH
}

data class RiskAnalysis(val risk: RiskEnum)