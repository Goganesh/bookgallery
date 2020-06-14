package com.library.bookgallery.service;

import com.library.bookgallery.conf.security.MyUserPrincipal;
import com.library.bookgallery.domain.Authority;
import com.library.bookgallery.domain.User;
import com.library.bookgallery.repository.AuthorityRepository;
import com.library.bookgallery.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        List<Authority> authorities = authorityRepository.findByUsername(username);

        return new MyUserPrincipal(user, authorities);
    }
}
