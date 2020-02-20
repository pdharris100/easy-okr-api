package com.easyokr.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyokr.exception.DomainNotFoundException;
import com.easyokr.model.Domain;
import com.easyokr.model.Organisation;
import com.easyokr.repository.DomainRepository;
import com.easyokr.security.Utils;

@RestController
@CrossOrigin
@RequestMapping("/domains")
public class DomainsController {
	//private static final Logger LOG = LoggerFactory.getLogger(DomainsController.class);

	@Autowired
	private DomainRepository domainRepository;

	@PostMapping
	public Domain create(@RequestBody Domain domain) {
		domainRepository.save(domain);
		return domain;		
	}
	
	@GetMapping()
	public Iterable <Domain> read(JwtAuthenticationToken jat) {
		long orgId = Utils.extractOrgId(jat);
		Iterable <Domain> domains = this.domainRepository.findByOrg(new Organisation(orgId), Sort.by("name").ascending());
		return domains;
	}

	@GetMapping("/{id}")
	public Domain read(@PathVariable long id) throws DomainNotFoundException {
		Optional <Domain> opt = this.domainRepository.findById(id);
		if (opt.isPresent()) return opt.get();
		throw new DomainNotFoundException("Domain id: " + id);
	}
	
	@PutMapping("/{id}")
	public Domain update(@PathVariable long id, @RequestBody Domain domain) throws DomainNotFoundException {
		if (!this.domainRepository.existsById(id)) throw new DomainNotFoundException("Domain id: " + id);
		domain.setId(id);
		domainRepository.save(domain);
		return domain;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		domainRepository.deleteById(id);		
	}
	
}
