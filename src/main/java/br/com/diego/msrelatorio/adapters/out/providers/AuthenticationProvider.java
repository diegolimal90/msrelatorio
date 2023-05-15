package br.com.diego.msrelatorio.adapters.out.providers;

import br.com.diego.msrelatorio.adapters.out.mapper.TokenEntityMapper;
import br.com.diego.msrelatorio.adapters.out.mapper.UserEntityMapper;
import br.com.diego.msrelatorio.config.security.TokenRepository;
import br.com.diego.msrelatorio.config.security.UserRepository;
import br.com.diego.msrelatorio.config.security.entity.TokenEntity;
import br.com.diego.msrelatorio.config.security.entity.UserEntity;
import br.com.diego.msrelatorio.core.domain.Token;
import br.com.diego.msrelatorio.core.domain.User;
import br.com.diego.msrelatorio.ports.AuthenticationProviderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationProvider implements AuthenticationProviderPort {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public User saveUser(User user) {
        UserEntity entity = UserEntityMapper.from(user);
        return UserEntityMapper.to(userRepository.save(entity));
    }

    @Override
    public Token saveToken(Token token) {
        TokenEntity entity = TokenEntityMapper.from(token);
        return TokenEntityMapper.to(tokenRepository.save(entity));
    }

    @Override
    public User findByEmail(String email) {
        return UserEntityMapper.to(userRepository.findByEmail(email).get());
    }

    @Override
    public List<Token> findTokenByUser(String userId) {
        var listToken = tokenRepository.findByUserId(userId);
        return listToken.stream().map(TokenEntityMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<Token> saveTokens(List<Token> tokens) {
        var listToken = tokens.stream().map(TokenEntityMapper::from)
                .collect(Collectors.toList());
        return tokenRepository.saveAll(listToken).stream()
                .map(TokenEntityMapper::to)
                .collect(Collectors.toList());
    }
}
