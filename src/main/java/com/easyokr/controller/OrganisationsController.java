package com.easyokr.controller;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.easyokr.exception.OrganisationNotFoundException;
import com.easyokr.model.Organisation;
import com.easyokr.repository.OrganisationRepository;

@RestController
@CrossOrigin
@RequestMapping("/organisations")
public class OrganisationsController {
	//private static final Logger LOG = LoggerFactory.getLogger(OrganisationsController.class);

	@Autowired
	private OrganisationRepository organisationRepository;

	@PostMapping
	public Organisation create(@RequestBody Organisation organisation) {
		organisationRepository.save(organisation);
		return organisation;		
	}
	
	@GetMapping()
	public Iterable <Organisation> read() {
		Iterable <Organisation> organisations = this.organisationRepository.findAll(Sort.by("name").ascending());
		return organisations;
	}
	
	@GetMapping("/{id}")
	public Organisation read(@PathVariable long id) throws OrganisationNotFoundException {
		Optional <Organisation> opt = this.organisationRepository.findById(id);
		if (opt.isPresent()) return opt.get();
		throw new OrganisationNotFoundException("Organisation id: " + id);
	}
	
	@PutMapping("/{id}")
	public Organisation update(@PathVariable long id, @RequestBody Organisation organisation) throws OrganisationNotFoundException {
		if (!this.organisationRepository.existsById(id)) throw new OrganisationNotFoundException("Organisation id: " + id);
		organisation.setId(id);
		organisationRepository.save(organisation);
		return organisation;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		organisationRepository.deleteById(id);		
	}
}
