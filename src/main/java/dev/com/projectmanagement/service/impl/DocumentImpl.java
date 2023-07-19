package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.repository.DocumentRepository;
import dev.com.projectmanagement.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DocumentImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Override
    public String store(MultipartFile file) throws IOException {
        Document document = documentRepository.save(Document.builder()
                        .name(file.getOriginalFilename())
                        .description("")
                        .docData(file.getBytes())
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
        byte[] file = doc.get().getDocData();

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
