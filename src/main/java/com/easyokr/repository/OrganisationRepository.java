package com.easyokr.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.easyokr.model.Domain;
import com.easyokr.model.Organisation;

public interface OrganisationRepository extends PagingAndSortingRepository<Organisation, Long>{}


