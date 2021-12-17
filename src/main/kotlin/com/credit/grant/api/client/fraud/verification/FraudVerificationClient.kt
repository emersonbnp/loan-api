package com.credit.grant.api.client.fraud.verification

import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class FraudVerificationClient {
    suspend fun checkFraud(clientId: Long): FraudVerification {
        delay(10000)
        return FraudVerification(isFraud = false)
    }
}