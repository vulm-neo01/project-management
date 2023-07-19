package dev.com.projectmanagement.model.register;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Id
    private String userId;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String jobPosition;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String email;
    @NotEmpty
    private LocalDate birthday;
    @NotEmpty
    private String address;
}
