package com.easyokr.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easyokr.exception.ObjectiveNotFoundException;
import com.easyokr.interceptor.Utils;
import com.easyokr.model.Domain;
import com.easyokr.model.Objective;
import com.easyokr.model.Organisation;
import com.easyokr.repository.ObjectiveRepository;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@CrossOrigin
@RequestMapping("/objectives")
public class ObjectivesController {
	// private static final Logger LOG =
	// LoggerFactory.getLogger(ObjectivesController.class);

	@Autowired
	private ObjectiveRepository objectiveRepository;

	@PostMapping
	public Objective create(HttpServletRequest request, @RequestBody Objective objective) {
		Organisation org = Utils.getOrg(request);
		if (objective.isPartOf(org)) {
			this.objectiveRepository.save(objective);
		}
		return objective;
	}

	@GetMapping()
	public Iterable<Objective> readByDomainId(HttpServletRequest request, @RequestParam(value = "domainId", defaultValue = "") Long domainId) {
		Organisation org = Utils.getOrg(request);
		Iterable<Objective> objectives;
		if (domainId == null) {
			objectives = this.objectiveRepository.findByOrg(org, Sort.by("description").ascending());
		} else {
			Domain domain = new Domain(domainId);
			objectives = this.objectiveRepository.findByDomainAndOrg(domain, org);
		}
		return objectives;
	}

	@GetMapping("/{id}")
	public Objective read(HttpServletRequest request, @PathVariable long id) {
		Organisation org = Utils.getOrg(request);
		Optional<Objective> opt = this.objectiveRepository.findByIdAndOrg(id, org);
		if (opt.isPresent()) {
			return opt.get();
		};
		return null;
	}

	@PutMapping("/{id}")
	public Objective update(HttpServletRequest request, @PathVariable long id, @RequestBody Objective objective) {
		Organisation org = Utils.getOrg(request);
		if (this.objectiveRepository.existsByIdAndOrg(id, org)) {
			objective.setId(id);
			this.objectiveRepository.save(objective);
		};			
		return objective;
	}
	
	@DeleteMapping("/{id}")
	public void delete(HttpServletRequest request, @PathVariable long id) {
		Organisation org = Utils.getOrg(request);
		this.objectiveRepository.deleteByIdAndOrg(id, org);
	}
}
