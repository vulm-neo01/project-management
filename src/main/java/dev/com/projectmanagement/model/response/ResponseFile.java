package dev.com.projectmanagement.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private long size;
}
