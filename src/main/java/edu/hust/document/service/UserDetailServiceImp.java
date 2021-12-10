package edu.hust.document.service;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.hust.document.model.Role;
import edu.hust.document.model.User;
import edu.hust.document.repository.UserRepository;

@Service
public class UserDetailServiceImp implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Username not found!");
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        Set<Role> roles = (Set<Role>)user.getRoles();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities);
	}

}
