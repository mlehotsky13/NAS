package sk.stuba.fiit.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import sk.stuba.fiit.model.FileRecord;
import sk.stuba.fiit.service.USBStorageService;

@Controller
@RequestMapping("/storages")
public class StorageController {

    @Autowired
    private USBStorageService storageService;

    @GetMapping
    public String getStoragesPage(Model model) {

        model.addAttribute("storages", storageService.getAllUSBStorageDevices());

        return "storages";
    }

    @GetMapping("/details")
    public String getDetailsPage(@RequestParam("path") String path, Model model) {
        Path p = Paths.get(path).normalize();

        if (p.getNameCount() <= 2) {
            return "redirect:/storages";
        }

        List<FileRecord> fileRecords = storageService.getRecordsInDirectory(p);

        model.addAttribute("path", p);
        model.addAttribute("records", fileRecords);

        return "details";
    }

    @PostMapping("/createDir")
    public String createDir(//
            @RequestParam("path") String path, //
            @RequestParam("dirname") String dirname, //
            RedirectAttributes redirectAttributes, //
            Model model) {

        storageService.createDirectory(path, dirname);

        redirectAttributes.addAttribute("path", path);

        return "redirect:/storages/details";
    }

    @PostMapping("/upload/**")
    public String uploadFile(//
            HttpServletRequest request, //
            @RequestParam("file") MultipartFile file, //
            RedirectAttributes redirectAttributes) throws IOException {

        Path p = Paths.get(request.getRequestURL().toString());
        Path path = Paths.get(p.toString().substring(p.toString().indexOf("/upload") + 7, p.toString().length()));
        Path fullPath = path.resolve(Paths.get(file.getOriginalFilename()));

        storageService.uploadFile(fullPath, file.getBytes());

        redirectAttributes.addAttribute("path", fullPath.getParent().toString());

        return "redirect:/storages/details";
    }

    @PostMapping("/deleteRecord")
    public String deleteRecord(//
            @RequestParam("path") String path, //
            RedirectAttributes redirectAttributes) {

        Path p = storageService.deleteRecord(path);

        redirectAttributes.addAttribute("path", p.getParent().toString());

        return "redirect:/storages/details";
    }

    @PostMapping("/editRecord")
    public String editRecord(//
            @RequestParam("path") String path, //
            @RequestParam("newname") String newname, //
            RedirectAttributes redirectAttributes) {

        Path p = storageService.editRecord(path, newname);

        redirectAttributes.addAttribute("path", p.getParent().toString());

        return "redirect:/storages/details";
    }

    @GetMapping("/fileRecord/**")
    public void getFileRecord(HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException {
        Path p = Paths.get(UriUtils.decode(request.getRequestURL().toString(), "utf-8"));
        Path path = Paths.get(p.toString().substring(p.toString().indexOf("/fileRecord") + 11, p.toString().length()));

        response.getOutputStream().write(Files.readAllBytes(path));
    }

    @GetMapping("/eject")
    public String ejectStorage(@RequestParam("mountPoint") String mountPoint) throws InterruptedException {

        storageService.ejectStorage(mountPoint);

        Thread.currentThread().sleep(1_000);

        return "redirect:/storages";
    }
}
