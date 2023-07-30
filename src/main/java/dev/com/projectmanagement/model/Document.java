package dev.com.projectmanagement.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {
    @Id
    @Generated
    private String docId;
    private String rootId;
    private String name;
    private String description;
    private byte[] docData;
    private String type;
    private LocalDate uploadDate;
    private String uploadBy;

    public Document(String name, String description, String uploadBy, String type, String rootId) {
        this.name = name;
        this.description = description;
        this.uploadDate = LocalDate.now();
        this.uploadBy = String.valueOf(SecurityContextHolder.getContext().getAuthentication());
        this.type = type;
        this.rootId = rootId;
    }

    public Document(String description) {
        this.description = description;
    }
}
