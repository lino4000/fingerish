package com.lino4000.fingerish.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role{

	@Id @GeneratedValue 
	private Integer id;
	@Column(unique=true)
	private String title;
	private String desc;
	@OneToMany(mappedBy="role")
	private List<User> users;
}