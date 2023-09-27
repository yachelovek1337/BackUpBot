package backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;

public class BackupCleaner {

    private String backupDirectory;

    public BackupCleaner(String backupDirectory) {
        this.backupDirectory = backupDirectory;
    }

    public void cleanOldBackups() {
        try {
            File backupDir = new File(backupDirectory);
            File[] backupFiles = backupDir.listFiles();

            if (backupFiles != null) {
                for (File file : backupFiles) {
                    if (isOldBackup(file.toPath())) {
                        if (file.isDirectory()) {
                            deleteDirectory(file);
                        } else {
                            Files.delete(file.toPath());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    Files.delete(file.toPath());
                }
            }
        }

        Files.delete(directory.toPath());
    }

    private static boolean isOldBackup(Path backupPath) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(backupPath, BasicFileAttributes.class);
            FileTime createTime = attrs.creationTime();
            long currentTimeMillis = System.currentTimeMillis();
            long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 день в миллисекундах

            return createTime.toMillis() < currentTimeMillis - oneDayInMillis;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
