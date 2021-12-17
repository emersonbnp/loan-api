package com.credit.grant.api.repository

import com.credit.grant.api.entity.Loan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoanRepository : JpaRepository<Loan, Long>