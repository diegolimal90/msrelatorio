package br.com.diego.msrelatorio.adapters.out.mapper;

import br.com.diego.msrelatorio.config.security.entity.Role;
import br.com.diego.msrelatorio.config.security.entity.UserEntity;
import br.com.diego.msrelatorio.core.domain.User;

import java.util.Optional;

public class UserEntityMapper {

    public static User to(UserEntity mapper){
        return Optional.ofNullable(mapper).map(entity -> User.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole().toString())
                .build()
        ).orElse(new User());
    }

    public static UserEntity from(User mapper){
        return Optional.ofNullable(mapper).map(domain -> UserEntity.builder()
                .id(domain.getId())
                .firstname(domain.getFirstname())
                .lastname(domain.getLastname())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .role(Role.valueOf(domain.getRole()))
                .build()
        ).orElse(new UserEntity());
    }
}
