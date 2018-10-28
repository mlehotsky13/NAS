package sk.stuba.fiit.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/details")
    public String getDetailsPage(@RequestParam("path") String path, Model model) throws IOException {
        Path p = Paths.get(path).normalize();

        if (p.getNameCount() <= 2) {
            return "redirect:/storages";
        }

        Set<FileRecord> records = Files.walk(p, 1)//
                .filter(v -> !v.equals(p))//
                .map(FileRecord::new)//
                .collect(Collectors.toSet());

        model.addAttribute("path", p);
        model.addAttribute("records", records);

        return "details";
    }

    @PostMapping("/createDir")
    public String createDir(//
            @RequestParam("path") String path, //
            @RequestParam("dirname") String dirname, //
            RedirectAttributes redirectAttributes, //
            Model model) throws IOException {

        Path p = Paths.get(path, dirname);
        Files.createDirectory(p);

        redirectAttributes.addAttribute("path", path);

        return "redirect:/storages/details";
    }

    @PostMapping("/deleteRecord")
    public String deleteRecord(//
            @RequestParam("path") String path, //
            RedirectAttributes redirectAttributes) throws IOException {

        Path p = Paths.get(path);
        FileSystemUtils.deleteRecursively(p);

        redirectAttributes.addAttribute("path", p.getParent().toString());

        return "redirect:/storages/details";
    }

    @PostMapping("/editRecord")
    public String editRecord(//
            @RequestParam("path") String path, //
            @RequestParam("newname") String newname, //
            RedirectAttributes redirectAttributes) throws IOException {
        
        Path p = Paths.get(path);
        Files.move(p, p.resolveSibling(newname));

        redirectAttributes.addAttribute("path", p.getParent().toString());

        return "redirect:/storages/details";
    }

    @GetMapping("/fileRecord")
    public byte[] getFileRecord(@RequestParam("path") String path, Model model) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }
}
