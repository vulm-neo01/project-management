package dev.com.projectmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discussion {
    private String discussionId;
    private String rootId;
    private String email;
    private String comments;
    private LocalDateTime time;
}
