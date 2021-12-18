package com.credit.grant.api.repository

import com.credit.grant.api.entity.Client
import com.credit.grant.api.entity.Loan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LoanRepository : JpaRepository<Loan, Long> {
    fun findByClient(client: Client): Optional<List<Loan>>
}