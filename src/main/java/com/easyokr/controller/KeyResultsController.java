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
import org.springframework.web.bind.annotation.RestController;

import com.easyokr.exception.KeyResultNotFoundException;
import com.easyokr.interceptor.Utils;
import com.easyokr.model.KeyResult;
import com.easyokr.model.Organisation;
import com.easyokr.repository.KeyResultRepository;

@RestController
@CrossOrigin
@RequestMapping("/keyResults")
public class KeyResultsController {
	private static final Logger LOG = LoggerFactory.getLogger(KeyResultsController.class);

	@Autowired
	private KeyResultRepository keyResultRepository;

	@PostMapping
	public KeyResult create(HttpServletRequest request, @RequestBody KeyResult keyResult) {
		Organisation org = Utils.getOrg(request);
		if (keyResult.isPartOf(org)) {
			this.keyResultRepository.save(keyResult);
		};
		return keyResult;		
	}
	
	@GetMapping()
	public Iterable <KeyResult> read(HttpServletRequest request) {
		Organisation org = Utils.getOrg(request);
		Iterable <KeyResult> keyResults = this.keyResultRepository.findByOrg(org, Sort.by("description").ascending());
		return keyResults;
	}
	
	@GetMapping("/{id}")
	public KeyResult read(HttpServletRequest request, @PathVariable long id) {
		Organisation org = Utils.getOrg(request);
		Optional <KeyResult> opt = this.keyResultRepository.findByIdAndOrg(id, org);
		if (opt.isPresent()) return opt.get();
		return null;
	}
	
	@PutMapping("/{id}")
	public KeyResult update(HttpServletRequest request, @PathVariable long id, @RequestBody KeyResult keyResult) throws KeyResultNotFoundException {
		Organisation org = Utils.getOrg(request);
		if (this.keyResultRepository.existsByIdAndOrg(id, org)) {
			keyResult.setId(id);
			this.keyResultRepository.save(keyResult);
		};			
		return keyResult;
	}
	
	@DeleteMapping("/{id}")
	public void delete(HttpServletRequest request, @PathVariable long id) {
		Organisation org = Utils.getOrg(request);
		this.keyResultRepository.deleteByIdAndOrg(id, org);		
	}
}
