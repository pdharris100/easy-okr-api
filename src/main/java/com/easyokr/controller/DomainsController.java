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
	public Domain create(@RequestBody Domain domain) {
		domainRepository.save(domain);
		return domain;
	}

	@GetMapping()
	public Iterable<Domain> read(HttpServletRequest request) {
		Organisation org = createOrg(request);
		Iterable<Domain> domains = this.domainRepository.findByOrg(org, Sort.by("name").ascending());
		return domains;
	}

	@GetMapping("/{id}")
	public Domain read(HttpServletRequest request, @PathVariable long id) throws DomainNotFoundException {
		Organisation org = createOrg(request);
		Optional<Domain> opt = this.domainRepository.findByIdAndOrg(id, org);
		if (opt.isPresent())
			return opt.get();
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

	private boolean validateIntegrity(HttpServletRequest request, Domain domain) throws OrganisationNotFoundException {
		Long orgId = (Long) request.getAttribute("tenantId");
		Optional<Organisation> opt = this.orgRepository.findById(orgId);
		if (opt.isPresent()) {
			return domain.isPartOf(opt.get());
		}
		throw new OrganisationNotFoundException("Organisation id: " + orgId);
	}

	private Organisation createOrg(HttpServletRequest request) {
		Long orgId = (Long) request.getAttribute("tenantId");
		return new Organisation(orgId);
	}

}
