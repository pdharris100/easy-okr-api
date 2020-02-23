package com.easyokr.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import com.easyokr.exception.OrganisationNotFoundException;
import com.easyokr.exception.TenantMismatchException;
import com.easyokr.model.Domain;
import com.easyokr.model.Organisation;
import com.easyokr.repository.DomainRepository;
import com.easyokr.repository.OrganisationRepository;
import com.easyokr.security.Utils;

@RestController
@CrossOrigin
@RequestMapping("/domains")
public class DomainsController {
	// private static final Logger LOG =
	// LoggerFactory.getLogger(DomainsController.class);

	@Autowired
	private DomainRepository domainRepository;
	@Autowired
	private OrganisationRepository orgRepository;

	@PostMapping
	public Domain create(HttpServletRequest request, @RequestBody Domain domain) {
		Organisation org = extractOrg(request);
		if (domain.isPartOf(org)) {
			domainRepository.save(domain);
		};
		return domain;
	}

	@GetMapping()
	public Iterable<Domain> read(HttpServletRequest request) {
		Organisation org = extractOrg(request);
		Iterable<Domain> domains = this.domainRepository.findByOrg(org, Sort.by("name").ascending());
		return domains;
	}

	@GetMapping("/{id}")
	public Domain read(HttpServletRequest request, @PathVariable long id) {
		Organisation org = extractOrg(request);
		Optional<Domain> opt = this.domainRepository.findByIdAndOrg(id, org);
		if (opt.isPresent()) {
			return opt.get();
		};
		return null;
	}

	@PutMapping("/{id}")
	public Domain update(HttpServletRequest request, @PathVariable long id, @RequestBody Domain domain) {
		Organisation org = extractOrg(request);
		if (domain.isPartOf(org) && this.domainRepository.existsById(id)) {
			domain.setId(id);
			domainRepository.save(domain);
		};			
		return domain;
	}

	@DeleteMapping("/{id}")
	public void delete(HttpServletRequest request, @PathVariable long id) {
		Organisation org = extractOrg(request);
		if (this.domainRepository.existsByIdAndOrg(id, org)) {
			domainRepository.deleteById(id);
		};
	}

	private Organisation extractOrg(HttpServletRequest request) {
		Organisation org = (Organisation) request.getAttribute("org");
		return org;
	}

}
