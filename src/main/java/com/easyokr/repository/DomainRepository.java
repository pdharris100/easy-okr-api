package com.easyokr.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.easyokr.model.Domain;
import com.easyokr.model.Organisation;

public interface DomainRepository extends PagingAndSortingRepository<Domain, Long>{

	Iterable<Domain> findByOrg(Organisation organisation, Sort sort);}


