package ru.aldonin.archiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static void createNewZip(String filename, String archiveName) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(archiveName));
             FileInputStream fileInputStream = new FileInputStream(filename);) {
            ZipEntry entry1 = new ZipEntry("notes.txt");
            zipOutputStream.putNextEntry(entry1);
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            // добавляем содержимое к архиву
            zipOutputStream.write(buffer);
            // закрываем текущую запись для новой записи
            zipOutputStream.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String extractFileName(String path) {
        String[] arr = path.split("\\\\");
        return arr[arr.length - 1];
    }


    public static void Zip(String sourceDir, String zipFile) throws Exception {
        // Cоздание объекта ZipOutputStream из FileOutputStream
        FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

        // Создание объекта File object архивируемой директории
        File fileSource = new File(sourceDir);

        addDirectory(zipOutputStream, fileSource);

        // Закрываем ZipOutputStream
        zipOutputStream.close();

        System.out.println("Zip файл создан!");
    }

    public static void addDirectory (ZipOutputStream zipOutputStream, File fileSource) throws Exception {

        File[] files = fileSource.listFiles();

        System.out.println("Добавление <" + fileSource.getName() + ">");

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                addDirectory(zipOutputStream, files[i]);
                continue;
            }
            System.out.println("Добавление файла <" + files[i].getName() + ">");

            FileInputStream fileInputStream = new FileInputStream(files[i]);

            zipOutputStream.putNextEntry(new ZipEntry(files[i].getPath()));

            byte[] buffer = new byte[fileInputStream.available()];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0)
                zipOutputStream.write(buffer, 0, length);
            zipOutputStream.closeEntry();
            fileInputStream.close();
        }
    }
}