package com.alessiodp.core.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "schema_history")
public class SchemaHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "version", nullable = false)
	private int version;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "script_name", nullable = false)
	private String scriptName;
	
	@Column(name = "install_date", nullable = false)
	private long installDate;
}
