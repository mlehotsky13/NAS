package sk.stuba.fiit.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import net.samuelcampos.usbdrivedetector.USBStorageDevice;
import net.samuelcampos.usbdrivedetector.detectors.AbstractStorageDeviceDetector;
import sk.stuba.fiit.model.FileRecord;

public class USBStorageService {

    public List<USBStorageDevice> getAllUSBStorageDevices() {
        return AbstractStorageDeviceDetector.getInstance().getStorageDevicesDevices();
    }

    public List<FileRecord> getRecordsInDirectory(Path p) {
        List<FileRecord> fileRecords = new ArrayList<>();

        try {
            fileRecords = Files.walk(p, 1)//
                    .filter(v -> !v.equals(p))//
                    .map(FileRecord::new)//
                    .sorted(Comparator.comparing((FileRecord v) -> v.getType()).reversed()
                            .thenComparing((FileRecord v) -> v.getName().toLowerCase()))//
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileRecords;
    }

    public Path createDirectory(String path, String dirName) {
        Path p = Paths.get(path).resolve(dirName);

        try {
            Files.createDirectory(p);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p;
    }

    public Path uploadFile(String requestURL, MultipartFile file) {
        Path p = Paths.get(requestURL);
        Path path = Paths.get(p.toString().substring(p.toString().indexOf("/upload") + 7, p.toString().length()));
        String fileName = file.getOriginalFilename();

        try {
            path = Files.createFile(path.resolve(fileName));
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path.toString()))) {
                bos.write(file.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    public Path deleteRecord(String path) {
        Path p = Paths.get(path);

        try {
            FileSystemUtils.deleteRecursively(p);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p;
    }

    public Path editRecord(String path, String newName) {
        Path p = Paths.get(path);

        try {
            Files.move(p, p.resolveSibling(newName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p;
    }

    public Path ejectStorage(String path) {
        Path p = Paths.get(path);

        try {
            Runtime.getRuntime().exec("umount " + p.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p;
    }
}
