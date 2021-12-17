package com.credit.grant.api.controller

import com.credit.grant.api.dto.ScoreUpdatedEvent
import com.credit.grant.api.producer.ScoreUpdateProducer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/score")
class ScoreController(private val scoreUpdateProducer: ScoreUpdateProducer) {

    @PutMapping
    fun updateClientById(@Valid @RequestBody newScore: ScoreUpdatedEvent
    ): ResponseEntity<Void> {
        scoreUpdateProducer.send(newScore)
        return ResponseEntity.ok(null)
    }
}