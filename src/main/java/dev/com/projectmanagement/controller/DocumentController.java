package dev.com.projectmanagement.controller;

import com.google.common.io.Files;
import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.model.response.ResponseFile;
import dev.com.projectmanagement.service.DocumentService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    @Autowired
    private final DocumentService documentService;

        @PostMapping("/project")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("projectId") String projectId) throws IOException {
        // Kiểm tra loại file
        String fileName = file.getOriginalFilename();
        String fileExtension = Files.getFileExtension(fileName);

        String upload = documentService.storeToProject(file, projectId);
        return ResponseEntity.status(HttpStatus.OK).body(upload);
    }

    @PostMapping("/task")
    public ResponseEntity<String> handleFileUploadTask(@RequestParam("file") MultipartFile file,@RequestParam("taskId") String taskId) throws IOException {
        // Kiểm tra loại file
        String fileName = file.getOriginalFilename();
        String fileExtension = Files.getFileExtension(fileName);

        String upload = documentService.storeToTask(file, taskId);
        return ResponseEntity.status(HttpStatus.OK).body(upload);
    }

    @GetMapping
    public ResponseEntity<List<ResponseFile>> getListFiles(){
        List<ResponseFile> files = documentService.getAllFiles().map(doc ->{
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(doc.getDocId())
                    .toUriString();

            return new ResponseFile(
                    doc.getName(),
                    fileDownloadUri,
                    doc.getType(),
                    doc.getDocData().length
            );
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) throws IOException {
        Document doc = documentService.getFile(id);

        String encodedFileName = URLEncoder.encode(doc.getName(), StandardCharsets.UTF_8.toString());


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(doc.getDocData());
    }

}
