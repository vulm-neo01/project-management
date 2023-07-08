package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.config.FileUtils;
import dev.com.projectmanagement.config.StorageProperties;
import dev.com.projectmanagement.exception.StorageException;
import dev.com.projectmanagement.exception.StorageFileNotFoundException;
import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.repository.DocumentRepository;
import dev.com.projectmanagement.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DocumentImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

//    @Autowired
//    private final Path rootLocation;
//
//    public DocumentImpl(Path rootLocation) {
//        this.rootLocation = rootLocation;
//    }
//
//    @Autowired
//    public DocumentImpl(StorageProperties properties) {
//        this.rootLocation = Paths.get(properties.getLocation());
//    }

//    @Override
//    public List<Document> findAll(){
//        return documentRepository.findAll();
//    }
//
//    @Override
//    public Optional<Document> findById(String id){
//        return documentRepository.findById(id);
//    }
//
//    @Override
//    public String insertNew(Document document){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentUserId = authentication.getName();
//        document.setUploadBy(currentUserId);
//        return documentRepository.save(document).toString();
//    }
//
//    @Override
//    public String update(Document document) {
//        Optional<Document> changingDoc = documentRepository.findById(document.getDocId());
//        Document documentUpdated = changingDoc.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));
//
//        documentUpdated.setDescription(document.getDescription());
//
//        return documentRepository.save(documentUpdated).toString();
//    }
//
//    @Override
//    public void delete(String id){
//        if(!documentRepository.existsById(id)){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID is not existence!");
//        }
//        documentRepository.deleteById(id);
//    }

    @Override
    public String store(MultipartFile file) throws IOException {
        Document document = documentRepository.save(Document.builder()
                        .name(file.getOriginalFilename())
                        .description("")
                        .docData(FileUtils.compressFile(file.getBytes()))
                        .type(file.getContentType())
                        .uploadBy(String.valueOf(SecurityContextHolder.getContext().getAuthentication()))
                        .uploadDate(LocalDate.now())
                .build());
        if(document != null){
            return "file upload successfully: " + file.getOriginalFilename();
        }
        return "File upload fail";
    }

    @Override
    public byte[] downloadFile(String id){
        Optional<Document> doc = documentRepository.findById(id);
        byte[] file = FileUtils.decompressFile(doc.get().getDocData());

        return file;
    }

    @Override
    public Document getFile(String id){
        return documentRepository.findById(id).get();
    };

    @Override
    public Stream<Document> getAllFiles(){
        return documentRepository.findAll().stream();
    };


//    @Override
//    public Path load(String filename) {
//        return rootLocation.resolve(filename);
//    }
//
//    @Override
//    public Resource loadAsResource(String filename) {
//        try {
//            Path file = load(filename);
//            Resource resource = new UrlResource(file.toUri());
//            if (resource.exists() || resource.isReadable()) {
//                return resource;
//            }
//            else {
//                throw new StorageFileNotFoundException(
//                        "Could not read file: " + filename);
//
//            }
//        }
//        catch (MalformedURLException e) {
//            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
//        }
//    }
//
//    @Override
//    public Stream<Path> loadAll() {
//        try {
//            return Files.walk(this.rootLocation, 1)
//                    .filter(path -> !path.equals(this.rootLocation))
//                    .map(this.rootLocation::relativize);
//        }
//        catch (IOException e) {
//            throw new StorageException("Failed to read stored files", e);
//        }
//    }
//
//    @Override
//    public void deleteAll() {
//        FileSystemUtils.deleteRecursively(rootLocation.toFile());
//    }
//
//    @Override
//    public void init() {
//        try {
//            Files.createDirectories(rootLocation);
//        }
//        catch (IOException e) {
//            throw new StorageException("Could not initialize storage", e);
//        }
//    }
}
