package com.easyokr.repository;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.easyokr.model.Domain;
import com.easyokr.model.Objective;
import com.easyokr.model.Organisation;

public interface ObjectiveRepository extends PagingAndSortingRepository<Objective, Long>{
	
	Iterable<Objective> findByDomainAndOrg(Domain domain, Organisation org);
	
	Iterable<Objective> findByOrg(Organisation organisation, Sort sort);

	Optional<Objective> findByIdAndOrg(long id, Organisation organisation);
	
	boolean existsByIdAndOrg(long id, Organisation organisation);

	@Transactional
	void deleteByIdAndOrg(long id, Organisation organisation);

		
}
