package eu.miroslavlehotsky.nas.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.Data;

@Data
public class FileRecord {

    private Path path;
    private String name;
    private String type;
    private String owner;
    private LocalDateTime lastModified;

    public FileRecord(Path path) {
        try {
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

            this.path = path;
            this.name = path.getFileName().toString();
            this.type = Files.isRegularFile(path) == true ? "File" : "Folder";
            this.owner = Files.getOwner(path).getName();
            this.lastModified =
                    LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneId.systemDefault());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
