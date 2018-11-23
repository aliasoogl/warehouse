
package com.sample.warehouse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.warehouse.domain.Deal;

/**
 * Repository of warehouse
 * @author Pranish Dahal
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Deal, Integer> {

	/**
	 * Finds whether the given file name exists or not.
	 * @param fileName
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	boolean existsByFileName(String fileName);

	/**
	 * Finds the deal by the filename
	 * @param fileName
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	Deal findByFileName(String fileName);

}
