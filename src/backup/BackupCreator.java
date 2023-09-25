package backup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupCreator {
    private String sourceDirectory;
    private String backupDirectory;

    public BackupCreator(String sourceDirectory, String backupDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.backupDirectory = backupDirectory;
    }

    public void createBackup() throws IOException {
        String backupName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Path sourcePath = Paths.get(sourceDirectory);
        Path backupPath = Paths.get(backupDirectory, backupName);

        Files.walk(sourcePath)
                .filter(Files::isRegularFile)
                .forEach(sourceFile -> {
                    try {
                        Path relativePath = sourcePath.relativize(sourceFile);
                        Path targetFile = backupPath.resolve(relativePath);

                        Files.createDirectories(targetFile.getParent());
                        Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
