package com.easyokr.repository;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.easyokr.model.Domain;
import com.easyokr.model.Objective;
import com.easyokr.model.Organisation;
import com.easyokr.model.Team;

public interface TeamRepository extends PagingAndSortingRepository<Team, Long>{

	Iterable<Team> findByDomainAndOrg(Domain domain, Organisation org, Sort sort);
	
	Iterable<Team> findByOrg(Organisation organisation, Sort sort);

	Optional<Team> findByIdAndOrg(long id, Organisation organisation);

	boolean existsByIdAndOrg(long id, Organisation organisation);
	
	@Transactional
	void deleteByIdAndOrg(long id, Organisation organisation);		
}


