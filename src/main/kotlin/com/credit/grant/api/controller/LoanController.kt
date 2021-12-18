package com.credit.grant.api.controller

import com.credit.grant.api.client.fraud.verification.FraudVerificationClient
import com.credit.grant.api.client.risk.analysis.RiskAnalysisClient
import com.credit.grant.api.client.risk.analysis.RiskEnum
import com.credit.grant.api.entity.Client
import com.credit.grant.api.request.LoanRequest
import com.credit.grant.api.entity.Loan
import com.credit.grant.api.repository.ClientRepository
import com.credit.grant.api.repository.LoanRepository
import com.credit.grant.api.response.LoanResponse
import javassist.NotFoundException
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/clients")
class LoanController(private val loanRepository: LoanRepository,
                     private val clientRepository: ClientRepository,
                     private val riskAnalysisClient: RiskAnalysisClient,
                     private val fraudVerificationClient: FraudVerificationClient) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/{clientId}/loans")
    fun createNewLoan(@Valid @PathVariable(value = "clientId") clientId: Long,
                              @Valid @RequestBody loanRequest: LoanRequest
    ): ResponseEntity<LoanResponse> {
        logger.info("Received creation for client {} request: {}", clientId, loanRequest)
        val client = clientRepository.findById(clientId)

        if (client.isPresent) {
            runBlocking {
                val init = System.currentTimeMillis()

                val riskAnalysis = async {
                    riskAnalysisClient.getRisk(clientId)
                }
                val fraudVerification = async {
                    fraudVerificationClient.checkFraud(clientId)
                }

                if (riskAnalysis.await().risk == RiskEnum.HIGH) throw Exception("Risk too high")
                if (fraudVerification.await().isFraud) throw Exception("Fraud attempt")

                val savedLoan = loanRepository.save(
                    Loan(
                        id = null,
                        client = client.get(),
                        value = loanRequest.value,
                        description = loanRequest.description,
                        status = loanRequest.status
                    )
                )
                val end = System.currentTimeMillis()
                logger.info("Loan operation took ${end - init} ms")
                return@runBlocking savedLoan.toLoanResponse()
            }
        } else {
            return notFound().build()
        }

        return ResponseEntity<LoanResponse>(HttpStatus.CREATED)
    }


    @GetMapping("/loans/{id}")
    fun getByLoanId(@PathVariable(value = "id") id: Long): ResponseEntity<LoanResponse> {
        return loanRepository.findById(id).map { loan ->
            ResponseEntity.ok(loan.toLoanResponse())
        }.orElse(notFound().build())
    }

    @GetMapping("/{clientId}/loans")
    fun getClientById(@PathVariable(value = "clientId") clientId: Long): ResponseEntity<List<LoanResponse>> {
        val loans = loanRepository.findByClient(Client(id = clientId)).orElseThrow{ throw NotFoundException("") }
        return ResponseEntity.ok(loans.map { loan -> loan.toLoanResponse() })
    }

    @DeleteMapping("/loans/{id}")
    fun deleteClientById(@PathVariable(value = "id") id: Long): ResponseEntity<Void> {

        return loanRepository.findById(id).map { loan  ->
            loan.deleted = true
            loanRepository.save(loan)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(notFound().build())

    }
}