package dev.com.projectmanagement.model;

import dev.com.projectmanagement.model.stable.UserRole;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User{
    @Id
    @Generated
    private String userId;
    private String username;
    private String password;
    private String fullName;
    private String jobPosition;
    private String phoneNumber;
    @Email
    private String email;
    private LocalDate birthday;
    private String address;
    private LocalDateTime createdDate;
    private UserRole role;
    private List<String> projectIds;
    private List<String> taskIds;
    private List<String> noteIds;
    public User(String username, String password, String fullName, String jobPosition, String phoneNumber, String email, LocalDate birthday, String address) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.jobPosition = jobPosition;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
        this.createdDate = LocalDateTime.now();
        this.role = UserRole.USER;
    }

    public User(String password) {
        this.password = password;
    }

    public User(String fullName, String jobPosition, String phoneNumber, String email, LocalDate birthday, String address) {
        this.fullName = fullName;
        this.jobPosition = jobPosition;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
    }

}
