package com.easyokr.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.easyokr.deserialization.ObjectiveDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@JsonDeserialize(using = ObjectiveDeserializer.class)
public class Objective {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@ManyToOne
    @JoinColumn(name="org_id")
	private Organisation org; 
	private String description;	
	@ManyToOne
    @JoinColumn(name="domain_id")
	private Domain domain;
	@OneToMany(mappedBy="objective",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<KeyResult> keyResults = new ArrayList<KeyResult>();
	
	protected Objective () {};
	
	public Objective (long id, Domain domain, String description) {
		this.id = id;
		this.domain = domain;
		this.description = description; 
	}

	public void addKeyResult(KeyResult keyResult) {
		this.keyResults.add(keyResult);
	}

	public void setId(long id) {
		this.id = id;		
	}	
	
}
