package br.com.diego.msrelatorio.config.security.entity;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@QueryEntity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("token")
public class TokenEntity {

    @Id
    public String id;

    public String token;

    public TokenType tokenType = TokenType.BEARER;

    public String userName;

    public boolean revoked;

    public boolean expired;

    public String userId;
}
