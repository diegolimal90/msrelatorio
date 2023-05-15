package br.com.diego.msrelatorio.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    public String id;

    public String token;

    public String userName;

    public boolean revoked;

    public boolean expired;

    public String userId;
}
