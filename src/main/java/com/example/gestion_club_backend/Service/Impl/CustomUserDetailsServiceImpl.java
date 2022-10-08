package com.example.gestion_club_backend.Service.Impl;

import com.example.gestion_club_backend.Model.Compte;
import com.example.gestion_club_backend.Repository.CompteRepository;
import com.example.gestion_club_backend.Service.CustomUserDetailsService;
import com.example.gestion_club_backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {
	@Autowired
	private CompteRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) {

		Compte user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with this username or email: %s", usernameOrEmail)));
		return UserPrincipal.create(user);

	}

	@Override
	@Transactional
	public UserDetails loadUserById(Long id) {

		Compte user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with id: %s", id)));
		return UserPrincipal.create(user);

	}
}
