package com.credit.grant.api.producer

import com.credit.grant.api.dto.ScoreUpdatedEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ScoreUpdateProducer(private val kafkaTemplate: KafkaTemplate<String, ScoreUpdatedEvent>) {

  fun send(scoreUpdateEvent: ScoreUpdatedEvent) {
    kafkaTemplate.send("score.updated.topic", scoreUpdateEvent.clientId.toString(), scoreUpdateEvent)
  }

}