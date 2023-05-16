package br.com.diego.msrelatorio.adpters.out;

import br.com.diego.msrelatorio.adapters.out.providers.AuthenticationProvider;
import br.com.diego.msrelatorio.config.security.TokenRepository;
import br.com.diego.msrelatorio.config.security.UserRepository;
import br.com.diego.msrelatorio.config.security.entity.Role;
import br.com.diego.msrelatorio.config.security.entity.TokenEntity;
import br.com.diego.msrelatorio.config.security.entity.UserEntity;
import br.com.diego.msrelatorio.core.domain.Token;
import br.com.diego.msrelatorio.core.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AuthenticationProviderTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;
    @InjectMocks
    private AuthenticationProvider authenticationProvider;

    @Test
    public void saveUserSuccess() {
        User user = User.builder()
                .id("123")
                .firstname("Fulano")
                .lastname("de Tal")
                .email("teste@teste.com")
                .password("123456")
                .role(Role.ADMIN.name())
                .build();

        UserEntity entity = UserEntity.builder()
                .id("123")
                .firstname("Fulano")
                .lastname("de Tal")
                .email("teste@teste.com")
                .password("123456")
                .role(Role.ADMIN)
                .build();
        Mockito.when(userRepository.save(any())).thenReturn(entity);

        var result = authenticationProvider.saveUser(user);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), entity.getId());
        Assertions.assertEquals(result.getEmail(), entity.getEmail());
    }

    @Test
    public void saveTokenSuccess() {
        Token token = Token.builder()
                .token("123")
                .expired(false)
                .revoked(false)
                .userName("Fulano")
                .id("456")
                .build();

        TokenEntity entity = TokenEntity.builder()
                .token("123")
                .expired(true)
                .revoked(true)
                .userName("Fulano")
                .id("456")
                .build();
        Mockito.when(tokenRepository.save(any())).thenReturn(entity);

        var result = authenticationProvider.saveToken(token);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), entity.getId());
        Assertions.assertTrue(result.isExpired());
        Assertions.assertTrue(result.isRevoked());
    }

    @Test
    public void findByEmailSuccess() {
        User user = User.builder()
                .firstname("Fulano")
                .lastname("de Tal")
                .email("teste@teste.com")
                .password("123456")
                .role(Role.ADMIN.name())
                .build();

        UserEntity entity = UserEntity.builder()
                .id("123")
                .firstname("Fulano")
                .lastname("de Tal")
                .email("teste@teste.com")
                .password("123456")
                .role(Role.ADMIN)
                .build();
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(entity));

        var result = authenticationProvider.findByEmail("teste@teste.com");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), entity.getId());
        Assertions.assertEquals(result.getEmail(), entity.getEmail());
    }

    @Test
    public void findTokenByUserSuccess() {
        List<Token> token = List.of(Token.builder()
                .token("123")
                .expired(false)
                .revoked(false)
                .userName("Fulano")
                .id("456")
                .userId("098")
                .build());

        List<TokenEntity> entity = List.of(TokenEntity.builder()
                .token("123")
                .expired(true)
                .revoked(true)
                .userName("Fulano")
                .id("456")
                .userId("098")
                .build());
        Mockito.when(tokenRepository.findByUserId(any())).thenReturn(entity);

        var result = authenticationProvider.findTokenByUser("098");

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(result.size(), entity.size());
    }

    @Test
    public void saveTokensSuccess() {
        List<Token> tokens = List.of(Token.builder()
                .token("123")
                .expired(false)
                .revoked(false)
                .userName("Fulano")
                .id("456")
                .userId("098")
                .build());

        List<TokenEntity> entities = List.of(TokenEntity.builder()
                .token("123")
                .expired(true)
                .revoked(true)
                .userName("Fulano")
                .id("456")
                .userId("098")
                .build());
        Mockito.when(tokenRepository.saveAll(any())).thenReturn(entities);

        var result = authenticationProvider.saveTokens(tokens);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(result.size(), entities.size());
    }
}
