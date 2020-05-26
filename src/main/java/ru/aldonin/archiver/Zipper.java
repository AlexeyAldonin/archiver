package ru.aldonin.archiver;


import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Zipper {

    private Zipper() {}

    public static void createZip (String userRequest) {
        boolean passwordRequired = userRequest.contains(RequestTemplate.PASSWORD.getRequest());
        boolean checkSumRequired = userRequest.contains(RequestTemplate.CHECKSUM.getRequest());
        boolean targetDirectoryDiffers = userRequest.contains(RequestTemplate.TARGET.getRequest());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

        String inputPath = UserInputRequest.requestForPath("исходного файла или директории с файлами");
        File inputFile = new File(inputPath);

        while (!inputFile.exists()) {
            inputPath = UserInputRequest.requestForPath("исходного файла или директории с файлами");
            inputFile = new File(inputPath);
        }

        List<File> filesToAdd = new ArrayList<>();
        collectAllFiles(inputPath, filesToAdd);

        String targetPath = "";

        if (targetDirectoryDiffers) {
            targetPath = UserInputRequest.requestForPath("конечной директории");
            File targetFile= new File(targetPath);

            while (!targetFile.exists()) {
                targetPath = UserInputRequest.requestForPath("конечной директории");
                targetFile= new File(targetPath);
            }
        } else targetPath = inputFile.isDirectory() ? inputPath : inputFile.getParent();

        ZipFile zipFile = new ZipFile(targetPath + "\\" + dateFormat.format(new Date()) + ".zip");
        ZipParameters parameters = new ZipParameters();
        if (passwordRequired) {
            setPassword(zipFile, parameters);
        }
        try {
            zipFile.addFiles(filesToAdd, parameters);
        } catch (ZipException ignore) {/*NOP*/} // Files are to be added successfully according to previous checks

        if (checkSumRequired) {
            try {
                CheckSumUtils.createCheckSumInFile(zipFile);
            } catch (IOException ignore) {/*NOP*/} //File and algorithm already checked
        }
    }

    private static void setPassword(ZipFile zipFile, ZipParameters parameters) {
        String password = UserInputRequest.askForPassword();
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
        zipFile.setPassword(password.toCharArray());
    }

    private static void collectAllFiles(String path, List<File> filesToAdd) {
        File source = new File(path);
        if (source.isFile()) filesToAdd.add(source);
        else  {
            File[] filesList = source.listFiles();
            if (filesList != null) {
                for (File file : filesList) {
                    collectAllFiles(file.getPath(), filesToAdd);
                }
            }
        }
    }


    public static boolean extractFilesToDirectory (String userRequest) {
        ZipFile zipFile = getZipFile();
        if (zipFile.getFile().getPath().equalsIgnoreCase("exit")) {
            return true;
        }

        while (!zipFile.getFile().getPath().equalsIgnoreCase("exit") && !zipFile.isValidZipFile()) {
            System.out.println("Указан некорретккный путь к архиву");
            zipFile = getZipFile();;
        }
        if (zipFile.getFile().getPath().equalsIgnoreCase("exit")) {
            return true;
        }

        String targetPath = UserInputRequest.requestForPath("конечной директории.");
        File targetDirectory = new File(targetPath);

        while (!(targetDirectory.isDirectory()) && !(targetDirectory.exists())
                && !targetDirectory.getPath().equalsIgnoreCase("exit")) {
            System.out.println("Указанная директория не существует.");
            targetPath = UserInputRequest.requestForPath("конечной директории.");
        }
        try {
            if (zipFile.isEncrypted()) {
                return finishExtractionWithPassword(zipFile, targetPath);
            }
        } catch (ZipException ignore) { /*NOP*/ } // Both archive and target directory are to be confirmed

        try {
            zipFile.extractAll(targetPath);
            return true;
        } catch (ZipException e) {
            System.out.println("Ошибка извлечения. Неверный пароль.");
            System.out.println(e.toString());
            return false;
        }
    }


    private static ZipFile getZipFile() {
        String archivePath = UserInputRequest.requestForPath("исходного архива.");
        if (archivePath.equalsIgnoreCase(RequestTemplate.HELP.getRequest())) {
            UserInputRequest.showHelp();
            getZipFile();
        }
        return new ZipFile(archivePath);
    }


    private static boolean finishExtractionWithPassword(ZipFile zipFile, String targetDirectory) {
        System.out.println("Введите пароль");
        String password = new Scanner(System.in).nextLine();
        zipFile.setPassword(password.toCharArray());
        try {
            zipFile.extractAll(targetDirectory);
        } catch (ZipException e) {

        }
        return true;
    }


    public static boolean repackAddPassword (String zipPath) {
        ZipFile zipFile = new ZipFile(zipPath);
        if (!zipFile.isValidZipFile()) {
            System.out.println("Указанный архив не найден");
            return false;
        }
        try {
            if (zipFile.isEncrypted()) {
                System.out.println("Данный архив уже защищён паролем");
                return true;
            } else {
                String password = new Scanner(System.in).nextLine();
                zipFile.setPassword(password.toCharArray());
                return true;
            }
        } catch (ZipException ignore) {/*NOP*/} //Path and file validation are already checked

        return true;
    }
}
