package backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

public class BackupCleaner {

    private String backupDirectory;

    public BackupCleaner(String backupDirectory) {
        this.backupDirectory = backupDirectory;
    }

    public void cleanOldBackups() {
        File backupDir = new File(backupDirectory);
        File[] backupFiles = backupDir.listFiles();

        if (backupFiles != null) {
            Arrays.stream(backupFiles)
                    .filter(this::isOldBackup)
                    .forEach(File::delete);
        }
    }

    private boolean isOldBackup(File backupFile) {
        BasicFileAttributes attrs = null;
        try {
            attrs = Files.readAttributes(backupFile.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long creationTimeMillis = attrs.creationTime().toMillis();
        long currentTimeMillis = System.currentTimeMillis();
        long oneDayInMillis = 24 * 60 * 60 * 1000;

        return currentTimeMillis - creationTimeMillis > oneDayInMillis;
    }

}
