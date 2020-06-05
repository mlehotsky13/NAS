package eu.miroslavlehotsky.nas.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.Data;

@Data
public class FileRecord {

	private Path path;
	private String name;
	private String type;
	private String size;
	private String owner;
	private LocalDateTime lastModified;

	public FileRecord(Path path) {
		try {
			this.path = path;
			name = path.getFileName().toString();
			type = Files.isRegularFile(path) == true ? "File" : "Folder";
			owner = Files.getOwner(path).getName();
			lastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneId.systemDefault());

			if ("File".equals(type))
				size = humanReadableByteCountSI(Files.size(path));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String humanReadableByteCountSI(long bytes) {
		if (-1000 < bytes && bytes < 1000) {
			return bytes + " B";
		}
		CharacterIterator ci = new StringCharacterIterator("kMGTPE");
		while (bytes <= -999_950 || bytes >= 999_950) {
			bytes /= 1000;
			ci.next();
		}
		return String.format("%.1f %cB", bytes / 1000.0, ci.current());
	}
}