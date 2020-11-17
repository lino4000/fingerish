package com.lino4000.fingerish.service;


import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lino4000.fingerish.model.User;
import com.lino4000.fingerish.repository.UserRepository;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;

    
    public UserService() {

    }
    
    public void save(User user) {
        User tuser = User.builder()
        	.username(user.getUsername())
//        	.password(argon2.hash(10, 65536, 1, user.getPassword().toCharArray() ))
//        	.isLogged(false)
        	.fingerPrint(user.getFingerPrint())
        	.isActive(true)
        	.build();
        userRepository.save(tuser);
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User verifyFingerPrint(FingerprintTemplate fgAttempt) {
    	FingerprintMatcher matcher = new FingerprintMatcher()
				.index(fgAttempt);
    	User match = null;
    	double high = 0;
    	for( User user: userRepository.findAll() ) {
    		double score = matcher.match(
    				new FingerprintTemplate(
			    		Base64.getDecoder().decode(
			    				user.getFingerPrint()
	    				)
			    	)
		    );
            if (score > high) {
                high = score;
                match = user;
            }
    	}
    	double threshold = 100;
	    return high >= threshold ? match : null;
   }
   
    public void hash() {
//    	System.out.println(argon2.hash(10, 65536, 1, "123456".toCharArray() ));
    }
}