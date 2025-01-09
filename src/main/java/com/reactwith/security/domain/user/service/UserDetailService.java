package com.reactwith.security.domain.user.service;

import com.reactwith.security.domain.user.dto.UserToken;
import com.reactwith.security.domain.user.entity.User;
import com.reactwith.security.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("UserDetailService.loadUserByUsername" + username);
        User user = userRepository.findByUsername(username).orElseThrow();
        UserToken userToken = UserToken.of(user);
        return userToken;
    }
}
