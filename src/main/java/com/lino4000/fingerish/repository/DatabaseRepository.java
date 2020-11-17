package com.lino4000.fingerish.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lino4000.fingerish.model.Database;
import com.lino4000.fingerish.model.Role;

public interface DatabaseRepository extends CrudRepository<Database, Long> {
	List<Database> findAllByRole(Role role);
	Database findByRole(Role role);

}
