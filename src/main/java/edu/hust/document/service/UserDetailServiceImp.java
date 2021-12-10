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

import edu.hust.document.entity.RoleEntity;
import edu.hust.document.entity.UserEntity;
import edu.hust.document.repository.UserRepository;

@Service
public class UserDetailServiceImp implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findUserByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("Username not found!");
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        Set<RoleEntity> roles = (Set<RoleEntity>)user.getRoles();
        for (RoleEntity role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), grantedAuthorities);
	}

}
