package br.com.diego.msrelatorio.adapters.out.mapper;

import br.com.diego.msrelatorio.config.security.entity.TokenEntity;
import br.com.diego.msrelatorio.config.security.entity.TokenType;
import br.com.diego.msrelatorio.core.domain.Token;

import java.util.Optional;

public class TokenEntityMapper {

    public static Token to(TokenEntity mapper){
        return Optional.ofNullable(mapper).map(entity -> Token.builder()
                .id(entity.getId())
                .token(entity.getToken())
                .userName(entity.getUserName())
                .revoked(entity.isRevoked())
                .expired(entity.isExpired())
                .userId(entity.getUserId())
                .build()
        ).orElse(new Token());
    }

    public static TokenEntity from(Token mapper){
        return Optional.ofNullable(mapper).map(domain -> TokenEntity.builder()
                .id(domain.getId())
                .token(domain.getToken())
                .userName(domain.getUserName())
                .revoked(domain.isRevoked())
                .tokenType(TokenType.BEARER)
                .expired(domain.isExpired())
                .userId(domain.getUserId())
                .build()
        ).orElse(new TokenEntity());
    }
}
