package eu.miroslavlehotsky.nas.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import eu.miroslavlehotsky.nas.model.FileRecord;
import net.samuelcampos.usbdrivedetector.USBStorageDevice;
import net.samuelcampos.usbdrivedetector.detectors.AbstractStorageDeviceDetector;

@Service
public class USBStorageServiceImpl implements USBStorageService {

	public List<USBStorageDevice> getAllUSBStorageDevices() {
		return AbstractStorageDeviceDetector.getInstance().getStorageDevicesDevices();
	}

	public List<FileRecord> getRecordsInDirectory(Path path) {
		List<FileRecord> fileRecords = new ArrayList<>();

		try {
			fileRecords = Files.walk(path, 1)//
					.filter(v -> !v.equals(path))//
					.map(FileRecord::new)//
					.sorted(Comparator.comparing((FileRecord v) -> v.getType()).reversed()
							.thenComparing((FileRecord v) -> v.getName().toLowerCase()))//
					.collect(Collectors.toCollection(ArrayList::new));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileRecords;
	}

	@PreAuthorize("hasRole('ROLE_RW_USER')")
	public Path createDirectory(String path, String dirName) {
		Path p = Paths.get(path).resolve(dirName);

		try {
			Files.createDirectory(p);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p;
	}

	@Async
	@PreAuthorize("hasRole('ROLE_RW_USER')")
	public Path uploadFile(Path path, InputStream is) {
		try (OutputStream os = new BufferedOutputStream(new FileOutputStream(path.toFile(), true))) {
			IOUtils.copy(is, os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return path;
	}

	@PreAuthorize("hasRole('ROLE_RW_USER')")
	public Path deleteRecord(String path) {
		Path p = Paths.get(path);

		try {
			FileSystemUtils.deleteRecursively(p);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p;
	}

	@PreAuthorize("hasRole('ROLE_RW_USER')")
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