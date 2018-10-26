package sk.stuba.fiit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getDetailsPage(@RequestParam("path") String path, Model model) {

        model.addAttribute("path", path.replaceAll("/media/miroslav/", ""));

        return "details";
    }
}
