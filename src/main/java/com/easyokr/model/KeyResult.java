package com.easyokr.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class KeyResult {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@ManyToOne
    @JoinColumn(name="org_id")
	private Organisation org;
	@ManyToOne
    @JoinColumn(name="obj_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Objective objective;
	private String description;
	private long baseline;
	private long target;
	private long current;
	private Date start;
	private Date deadline;
	
	protected KeyResult () {}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setObjective(Objective objective) {
		this.objective = objective;		
	}

	public boolean isPartOf(Organisation organisation) {		
		return organisation.equals(this.org);
	};
}
