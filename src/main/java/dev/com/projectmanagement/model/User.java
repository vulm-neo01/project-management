package dev.com.projectmanagement.model;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
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
