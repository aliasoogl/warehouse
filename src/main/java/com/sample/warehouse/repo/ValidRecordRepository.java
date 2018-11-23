package com.sample.warehouse.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sample.warehouse.domain.ValidRecord;


@Repository
public interface ValidRecordRepository extends JpaRepository<ValidRecord, Long> {

	/**
	 * This uploads the provided file directly to the temporary table .
	 * @param fileName
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	@Modifying
	@Transactional
	@Query(value = "load data local infile ?1 into table valid_record FIELDS TERMINATED BY  ',' ", nativeQuery = true)
	void uploadFile(String fileName);

	/**
	 * Returns the records whose deal is provided.
	 * @param id
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	List<ValidRecord> findByDealId(Integer id);

}
