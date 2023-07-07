import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(100, 4, 25, 30.5);
        GameProgress progress2 = new GameProgress(80, 6, 15, 25.3);
        GameProgress progress3 = new GameProgress(50, 7, 30, 40.7);

        saveGames("D:\\Netology.project\\Блок JavaCore\\Games\\savegames\\save1.dat", progress1);
        saveGames("D:\\Netology.project\\Блок JavaCore\\Games\\savegames\\save2.dat", progress2);
        saveGames("D:\\Netology.project\\Блок JavaCore\\Games\\savegames\\save3.dat", progress3);

        zipFiles("D:\\Netology.project\\Блок JavaCore\\Games\\savegames\\zip.zip",
                List.of("D:\\Netology.project\\Блок JavaCore\\Games\\savegames\\save1.dat",
                        "D:\\Netology.project\\Блок JavaCore\\Games\\savegames\\save2.dat",
                        "D:\\Netology.project\\Блок JavaCore\\Games\\savegames\\save3.dat"));


    }

    public static void saveGames(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String zipFilePath, List<String> files) {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zout = new ZipOutputStream(fos)) {
            for (String failPath : files) {
                File file = new File(failPath);
                if (file.exists()) {
                    FileInputStream fin = new FileInputStream(file);
                    ZipEntry zipEn = new ZipEntry(file.getName());
                    zout.putNextEntry(zipEn);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fin.read(buffer)) > 0) {
                        zout.write(buffer, 0, length);
                    }
                    fin.close();
                    zout.closeEntry();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (String filePath : files) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}