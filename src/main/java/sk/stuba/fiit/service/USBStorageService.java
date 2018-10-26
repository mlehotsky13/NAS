package sk.stuba.fiit.service;

import java.util.List;

import net.samuelcampos.usbdrivedetector.USBStorageDevice;
import net.samuelcampos.usbdrivedetector.detectors.AbstractStorageDeviceDetector;

public class USBStorageService {

    public List<USBStorageDevice> getAllUSBStorageDevices() {
        return AbstractStorageDeviceDetector.getInstance().getStorageDevicesDevices();
    }
}
