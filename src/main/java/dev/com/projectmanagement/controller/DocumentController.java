package dev.com.projectmanagement.controller;

import com.google.common.io.Files;
import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.model.response.ResponseFile;
import dev.com.projectmanagement.repository.DocumentRepository;
import dev.com.projectmanagement.service.DocumentService;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.*;
import java.io.ByteArrayInputStream;
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

    @Autowired
    private final DocumentRepository documentRepository;

//    @GetMapping
//    public ResponseEntity<List<Document>> getAllDocument(){
//        return ResponseEntity.ok(documentService.findAll());
//    }

//    @GetMapping
//    public String listUploadedFiles(Model model) throws IOException{
//        model.addAttribute("files", documentService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(DocumentController.class,
//                        "serveFile", path.getFileName().toString()).build().toUri().toString())
//               .collect(Collectors.toList()));
//        return model.toString();
//    }

//    @GetMapping("/files/{filename:.+")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
//        Resource file = documentService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<Document>> getDocument(@PathVariable("id") String documentId){
//        return ResponseEntity.ok(documentService.findById(documentId));
//    }

//    @PostMapping
//    public ResponseEntity<String> saveDocument(@RequestBody Document document){
//        return ResponseEntity.ok(documentService.insertNew(document));
//    }

    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        // Kiểm tra loại file
        String fileName = file.getOriginalFilename();
        String fileExtension = Files.getFileExtension(fileName);
//        if (!fileExtension.equalsIgnoreCase("doc") && !fileExtension.equalsIgnoreCase("docx")) {
//            return ResponseEntity.badRequest().body("Only Word documents are allowed.");
//        }


        String upload = documentService.store(file);
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

//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException e){
//        return ResponseEntity.notFound().build();
//    }

//    @PostMapping("/update")
//    public ResponseEntity<String> updateDocument(@RequestBody Document document){
//        return ResponseEntity.ok(documentService.update(document));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteDocument(@PathVariable("id") String documentId){
//        documentService.delete(documentId);
//        return ResponseEntity.accepted().build();
//    }
}
