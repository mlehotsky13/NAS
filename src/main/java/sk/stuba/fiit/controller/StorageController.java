package sk.stuba.fiit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
