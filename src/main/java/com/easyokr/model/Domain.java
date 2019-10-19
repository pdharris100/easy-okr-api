package com.easyokr.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Domain {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
//	@OneToMany(mappedBy = "domain",
//			cascade = CascadeType.ALL,
//			orphanRemoval = true)
//	private List<Objective> objectives;
	
	protected Domain () {}

	public void setId(long id) {
		this.id = id;		
	};
}
