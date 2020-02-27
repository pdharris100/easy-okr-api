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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.easyokr.interceptor.Utils;
import com.easyokr.model.Team;
import com.easyokr.model.Domain;
import com.easyokr.model.Organisation;
import com.easyokr.repository.TeamRepository;

@RestController
@CrossOrigin
@RequestMapping("/teams")
public class TeamsController {
	// private static final Logger LOG =
	// LoggerFactory.getLogger(TeamsController.class);

	@Autowired
	private TeamRepository teamRepository;

	@PostMapping
	public Team create(HttpServletRequest request, @RequestBody Team team) {
		Organisation org = Utils.getOrg(request);
		if (team.isPartOf(org)) {
			this.teamRepository.save(team);
		};
		return team;
	}

	@GetMapping()
	public Iterable<Team> readByDomainId(HttpServletRequest request, @RequestParam(value = "domainId", defaultValue = "") Long domainId) {
		Organisation org = Utils.getOrg(request);
		Iterable<Team> teams;
		if (domainId == null) {
			teams = this.teamRepository.findByOrg(org, Sort.by("name").ascending());
		} else {
			Domain domain = new Domain(domainId);
			teams = this.teamRepository.findByDomainAndOrg(domain, org, Sort.by("name").ascending());
		}
		return teams;
	}

	@GetMapping("/{id}")
	public Team read(HttpServletRequest request, @PathVariable long id) {
		Organisation org = Utils.getOrg(request);
		Optional<Team> opt = this.teamRepository.findByIdAndOrg(id, org);
		if (opt.isPresent()) {
			return opt.get();
		};
		return null;
	}

	@PutMapping("/{id}")
	public Team update(HttpServletRequest request, @PathVariable long id, @RequestBody Team team) {
		Organisation org = Utils.getOrg(request);
		if (this.teamRepository.existsByIdAndOrg(id, org)) {
			team.setId(id);
			this.teamRepository.save(team);
		};			
		return team;
	}

	@DeleteMapping("/{id}")
	public void delete(HttpServletRequest request, @PathVariable long id) {
		Organisation org = Utils.getOrg(request);
		this.teamRepository.deleteByIdAndOrg(id, org);
	}
}
