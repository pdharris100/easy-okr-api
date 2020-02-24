package com.easyokr.repository;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.easyokr.model.Domain;
import com.easyokr.model.Organisation;

public interface DomainRepository extends PagingAndSortingRepository<Domain, Long>{

	Iterable<Domain> findByOrg(Organisation organisation, Sort sort);

	Optional<Domain> findByIdAndOrg(long id, Organisation organisation);

	boolean existsByIdAndOrg(long id, Organisation organisation);
	
	@Transactional
	void deleteByIdAndOrg(long id, Organisation organisation);		
}


