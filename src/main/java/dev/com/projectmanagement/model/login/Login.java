package dev.com.projectmanagement.model.login;

import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    @Email
    private String email;
    private String password;
}
