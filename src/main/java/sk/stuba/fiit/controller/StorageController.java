package sk.stuba.fiit.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sk.stuba.fiit.model.FileRecord;
import sk.stuba.fiit.service.USBStorageService;

@Controller
@RequestMapping("/storages")
public class StorageController {

    private USBStorageService storageService = new USBStorageService();

    @GetMapping
    public String getStoragesPage(Model model) {

        model.addAttribute("storages", storageService.getAllUSBStorageDevices());

        return "storages";
    }

    @PostMapping("/details")
    public String getDetailsPage(@RequestParam("path") String path, Model model) throws IOException {
        Path p = Paths.get(path);
        Set<FileRecord> records = Files.walk(p, 1)//
                .filter(v -> !v.equals(p))//
                .map(FileRecord::new)//
                .collect(Collectors.toSet());

        model.addAttribute("path", p.subpath(2, p.getNameCount()));
        model.addAttribute("records", records);

        return "details";
    }
}
