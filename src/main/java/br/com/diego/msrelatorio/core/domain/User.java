package br.com.diego.msrelatorio.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
}
