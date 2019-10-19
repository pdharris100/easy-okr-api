package com.easyokr.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyokr.exception.KeyResultNotFoundException;
import com.easyokr.model.KeyResult;
import com.easyokr.repository.KeyResultRepository;

@RestController
@CrossOrigin
@RequestMapping("/keyResults")
public class KeyResultsController {
	private static final Logger LOG = LoggerFactory.getLogger(KeyResultsController.class);

	@Autowired
	private KeyResultRepository keyResultRepository;

	@PostMapping
	public KeyResult create(@RequestBody KeyResult keyResult) {
		keyResultRepository.save(keyResult);
		return keyResult;		
	}
	
	@GetMapping()
	public Iterable <KeyResult> read() {
		Iterable <KeyResult> keyResults = this.keyResultRepository.findAll();
		return keyResults;
	}
	
	@GetMapping("/{id}")
	public KeyResult read(@PathVariable long id) throws KeyResultNotFoundException {
		Optional <KeyResult> opt = this.keyResultRepository.findById(id);
		if (opt.isPresent()) return opt.get();
		throw new KeyResultNotFoundException("KeyResult id: " + id);
	}
	
	@PutMapping("/{id}")
	public KeyResult update(@PathVariable long id, @RequestBody KeyResult keyResult) throws KeyResultNotFoundException {
		if (!this.keyResultRepository.existsById(id)) throw new KeyResultNotFoundException("KeyResult id: " + id);
		keyResult.setId(id);
		keyResultRepository.save(keyResult);
		return keyResult;
	}
}
