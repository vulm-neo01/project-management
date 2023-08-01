package dev.com.projectmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discussion {
    @Id
    @Generated
    private String discussionId;
    private String rootId;
    private String email;
    private String content;
    private LocalDateTime time;
}
