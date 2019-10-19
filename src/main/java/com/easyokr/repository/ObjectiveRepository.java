package com.easyokr.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.easyokr.model.Objective;

public interface ObjectiveRepository extends CrudRepository<Objective, Long>{
	
	@Query(value = "select * from objective where domain_id = ?1", nativeQuery = true)
	  Iterable<Objective> findByDomainId(long domainId);
	
}
