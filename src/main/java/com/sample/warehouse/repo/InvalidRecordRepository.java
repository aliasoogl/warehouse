package com.sample.warehouse.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sample.warehouse.domain.InvalidRecord;

/**
 * Repository for Invalid Records.
 * @author Pranish Dahal
 * 
 */
public interface InvalidRecordRepository extends JpaRepository<InvalidRecord, Long> {

	/**
	 * This uploads the provided file directly to the table .
	 * @param fileName
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	@Modifying
	@Transactional
	@Query(value = "load data local infile ?1 into table invalid_record FIELDS TERMINATED BY  ',' ", nativeQuery = true)
	void uploadFile(String fileName);

	/**
	 * Returns all the records for the dealId
	 * @param id
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	List<InvalidRecord> findByDealId(Integer id);
}
