package com.easyokr.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import com.easyokr.interceptor.Utils;
import com.easyokr.model.Domain;
import com.easyokr.model.Organisation;
import com.easyokr.repository.DomainRepository;

@RestController
@CrossOrigin
@RequestMapping("/domains")
public class DomainsController {
	// private static final Logger LOG =
	// LoggerFactory.getLogger(DomainsController.class);

	@Autowired
	private DomainRepository domainRepository;

	@PostMapping
	public Domain create(HttpServletRequest request, @RequestBody Domain domain) {
		Organisation org = Utils.getOrg(request);
		if (domain.isPartOf(org)) {
			this.domainRepository.save(domain);
		};
		return domain;
	}

	@GetMapping()
	public Iterable<Domain> read(HttpServletRequest request) {
		Organisation org = Utils.getOrg(request);
		Iterable<Domain> domains = this.domainRepository.findByOrg(org, Sort.by("name").ascending());
		return domains;
	}

	@GetMapping("/{id}")
	public Domain read(HttpServletRequest request, @PathVariable long id) {
		Organisation org = Utils.getOrg(request);
		Optional<Domain> opt = this.domainRepository.findByIdAndOrg(id, org);
		if (opt.isPresent()) {
			return opt.get();
		};
		return null;
	}

	@PutMapping("/{id}")
	public Domain update(HttpServletRequest request, @PathVariable long id, @RequestBody Domain domain) {
		Organisation org = Utils.getOrg(request);
		if (this.domainRepository.existsByIdAndOrg(id, org)) {
			domain.setId(id);
			this.domainRepository.save(domain);
		};			
		return domain;
	}

	@DeleteMapping("/{id}")
	public void delete(HttpServletRequest request, @PathVariable long id) {
		Organisation org = Utils.getOrg(request);
		this.domainRepository.deleteByIdAndOrg(id, org);
	}
}
