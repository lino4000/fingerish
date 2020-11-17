package com.lino4000.fingerish.repository;

import org.springframework.data.repository.CrudRepository;

import com.lino4000.fingerish.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
	User findById(Integer id);
}