package com.easyokr.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Domain {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@ManyToOne
    @JoinColumn(name="org_id")
	private Organisation org;
	private String name;
//	@OneToMany(mappedBy = "domain",
//			cascade = CascadeType.ALL,
//			orphanRemoval = true)
//	private List<Objective> objectives;
	
	protected Domain () {}
	
	public Domain (long id) {
		this.id = id;
	}

	public void setId(long id) {
		this.id = id;		
	}

	public boolean isPartOf(Organisation organisation) {		
		return organisation.equals(this.org);
	};
}
