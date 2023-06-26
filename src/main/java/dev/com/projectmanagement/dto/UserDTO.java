package dev.com.projectmanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {
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
    private LocalDateTime createdDate;
    private List<String> projectIds;
    private List<String> taskIds;
    private List<String> noteIds;

}
