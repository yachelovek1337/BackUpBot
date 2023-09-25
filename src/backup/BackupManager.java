package backup;

import java.io.*;

public class BackupManager {
    public static void main(String[] args) throws InterruptedException {
        String sourceDirectory = "/home/grief";
        String backupDirectory = "/home/backup_grief";

        BackupCreator backupCreator = new BackupCreator(sourceDirectory, backupDirectory);
        BackupCleaner backupCleaner = new BackupCleaner(backupDirectory);

        while (true) {
            try {
                System.out.println("Делаю бэкап " + sourceDirectory + " в " + backupDirectory);
                backupCreator.createBackup();
                System.out.println("Удаляю старые бэкапы");
                backupCleaner.cleanOldBackups();
                System.out.println("Жду 5 часов :)");
                Thread.sleep(5 * 60 * 60 * 1000);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
