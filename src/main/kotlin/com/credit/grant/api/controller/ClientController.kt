package com.credit.grant.api.controller

import com.credit.grant.api.entity.Client
import com.credit.grant.api.repository.ClientRepository
import com.credit.grant.api.request.ClientRequest
import com.credit.grant.api.response.ClientResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/clients")
class ClientController(private val clientRepository: ClientRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getAllClients(): List<ClientResponse> =
            clientRepository.findAll().map { it.toClientResponse() }


    @PostMapping
    fun createNewClient(@Valid @RequestBody client: ClientRequest): ClientResponse {
        logger.info("Received creation request: {}", client)
        val savedClient = clientRepository.save(Client(name = client.name, score = client.score))
        return savedClient.toClientResponse()
    }


    @GetMapping("/{id}")
    fun getClientById(@PathVariable(value = "id") id: Long): ResponseEntity<ClientResponse> {
        return clientRepository.findById(id).map { client ->
            ResponseEntity.ok(client.toClientResponse())
        }.orElse(ResponseEntity.notFound().build())
    }
}