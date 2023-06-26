package dev.com.projectmanagement.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {
    @Id
    @Generated
    private String docId;
    private String description;
    private LocalDate uploadDate;
    private String uploadBy;

    public Document(String description, String uploadBy) {
        this.description = description;
        this.uploadDate = LocalDate.now();
        this.uploadBy = uploadBy;
    }

    public Document(String description) {
        this.description = description;
    }
}
