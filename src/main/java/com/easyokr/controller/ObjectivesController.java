package com.easyokr.controller;

import java.util.Optional;

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
import com.easyokr.model.Objective;
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
	public Objective create(@RequestBody Objective objective) {
		objectiveRepository.save(objective);
		return objective;
	}

	@GetMapping()
	public Iterable<Objective> readByDomainId(@RequestParam(value = "domainId", defaultValue = "") Long domainId) {
		Iterable<Objective> objectives;
		if (domainId == null) {
			objectives = this.objectiveRepository.findAll(Sort.by("description").ascending());
		} else {
			objectives = this.objectiveRepository.findByDomainId(domainId);
		}
		return objectives;
	}

	@GetMapping("/{id}")
	public Objective read(@PathVariable long id) throws ObjectiveNotFoundException {
		Optional<Objective> opt = this.objectiveRepository.findById(id);
		if (opt.isPresent())
			return opt.get();
		throw new ObjectiveNotFoundException("Objective id: " + id);
	}

	@PutMapping("/{id}")
	public Objective update(@PathVariable long id, @RequestBody Objective objective) throws ObjectiveNotFoundException {
		if (!this.objectiveRepository.existsById(id))
			throw new ObjectiveNotFoundException("Objective id: " + id);
		objective.setId(id);
		objectiveRepository.save(objective);
		return objective;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		objectiveRepository.deleteById(id);		
	}
}
