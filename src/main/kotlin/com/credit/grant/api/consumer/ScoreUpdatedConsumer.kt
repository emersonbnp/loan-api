package com.credit.grant.api.consumer

import com.credit.grant.api.dto.ScoreUpdatedEvent
import com.credit.grant.api.repository.ClientRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope

@Component
class ScoreUpdatedConsumer(val clientRepository: ClientRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["score.updated.topic"], groupId = "score-updated-consumer")
    fun processMessage(scoreUpdatedEvent: ScoreUpdatedEvent) {

        logger.info("Received message: {}", scoreUpdatedEvent)

        clientRepository.findById(scoreUpdatedEvent.clientId)
            .ifPresentOrElse({
                    clientRepository.save(it.copy(score = scoreUpdatedEvent.score))
                }, {
                    logger.info("Client ${scoreUpdatedEvent.clientId} does not exist")
                })
    }
}
