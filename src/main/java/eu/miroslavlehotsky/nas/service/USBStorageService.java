package eu.miroslavlehotsky.nas.service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import eu.miroslavlehotsky.nas.model.FileRecord;
import net.samuelcampos.usbdrivedetector.USBStorageDevice;

public interface USBStorageService {

	/**
	 * Get all connected USB storage devices
	 * 
	 * @return all connected USB storage devices
	 */
	List<USBStorageDevice> getAllUSBStorageDevices();

	/**
	 * Get files and folders for given path
	 * 
	 * @param path
	 * @return files and folders for given path
	 */
	List<FileRecord> getRecordsInDirectory(Path path);

	/**
	 * Create new directory
	 * 
	 * @param path location where to create new directory
	 * @param dirName directory name
	 * @return path of newly created directory
	 */
	Path createDirectory(String path, String dirName);

	/**
	 * Upload file to given location
	 * 
	 * @param path location where to upload file
	 * @param is source input stream
	 * @return path to uploaded file
	 */
	Path uploadFile(Path path, InputStream is);

	/**
	 * Delete file or directory
	 * 
	 * @param path location of file or directory to delete
	 * @return path of deleted file or directory
	 */
	Path deleteRecord(String path);

	/**
	 * Edit file's or directory's name
	 * 
	 * @param path location of file or directory to edit
	 * @param newName new name of given file or directory
	 * @return path of edited file or directory
	 */
	Path editRecord(String path, String newName);

	/**
	 * Eject USB storage
	 * 
	 * @param path of USB storage to eject
	 * @return path of ejected USB storage device
	 */
	Path ejectStorage(String path);
}
