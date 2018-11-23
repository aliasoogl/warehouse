package com.sample.warehouse.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.warehouse.domain.CurrencySummary;

/**
 * Repository for Ordering currency summary
 * @author Pranish Dahal
 * 
 */
public interface CurrencySummaryRepository extends JpaRepository<CurrencySummary, Long> {

}
