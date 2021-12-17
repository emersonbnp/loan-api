package com.credit.grant.api.client.risk.analysis

import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class RiskAnalysisClient {
    suspend fun getRisk(clientId: Long): RiskAnalysis {
        delay(10000)
        return RiskAnalysis(RiskEnum.MEDIUM)
    }
}