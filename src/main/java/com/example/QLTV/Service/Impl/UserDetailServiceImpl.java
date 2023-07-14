package com.example.QLTV.Service.Impl;

import com.example.QLTV.Entity.Reader;
import com.example.QLTV.Repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    ReaderRepository readerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Reader> reader = Optional.ofNullable(readerRepository.findReaderByEmailAndActive(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("Reader not found")));

        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(reader.get().getRoles().toString()));

        return new User(reader.get().getEmail(),reader.get().getPassword(),authorities );
    }
}
