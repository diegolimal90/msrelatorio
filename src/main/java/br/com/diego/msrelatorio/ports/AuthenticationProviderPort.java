package br.com.diego.msrelatorio.ports;

import br.com.diego.msrelatorio.core.domain.Token;
import br.com.diego.msrelatorio.core.domain.User;

import java.util.List;

public interface AuthenticationProviderPort {
    User saveUser(User user);
    Token saveToken(Token token);
    User findByEmail(String Email);
    List<Token> findTokenByUser(String userId);
    List<Token> saveTokens(List<Token> tokens);
}
