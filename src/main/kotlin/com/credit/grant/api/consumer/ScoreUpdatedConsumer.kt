package com.credit.grant.api.consumer

import com.credit.grant.api.dto.ScoreUpdatedEvent
import com.credit.grant.api.repository.ClientRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ScoreUpdatedConsumer(val clientRepository: ClientRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["score.updated.topic"], groupId = "score-updated-consumer")
    fun processMessage(scoreUpdatedEvent: ScoreUpdatedEvent) {

        logger.info("Received message: {}", scoreUpdatedEvent)

        clientRepository.findById(scoreUpdatedEvent.clientId)
            .ifPresentOrElse({
                    it.score = scoreUpdatedEvent.score
                    clientRepository.save(it)
                }, {
                    logger.info("Client ${scoreUpdatedEvent.clientId} does not exist")
                })
    }
}
