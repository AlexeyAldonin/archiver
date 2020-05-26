package ru.aldonin.archiver;

import net.lingala.zip4j.ZipFile;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.security.MessageDigest.getInstance;

public class CheckSumUtils {

    public static final String ALGORITHM = "SHA-1";

    public static String getChecksum(String filepath) {

        File file = new File(filepath);
        while (!file.isFile()) {
            filepath = UserInputRequest.requestForPath("исходного архива");
            if (filepath.equalsIgnoreCase("exit")) {
                return filepath;
            } else {
                file = new File(filepath);
            }
        }
        MessageDigest md = null;
        try {
            md = getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException ignore) {/*NOP*/} //algorithm is checked

        //file hashing with DigestInputStream
        try (DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(filepath), md)) {
            while (digestInputStream.read() != -1) ;//empty loop to clear the data
            md = digestInputStream.getMessageDigest();
        } catch (IOException e) {
            return e.getMessage() + " Ошибка при чтении файла " + filepath;
        } //
        //bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        System.out.println("Контрольная сумма " + ALGORITHM + ": " + result);
        return result.toString();
    }


    public static File createCheckSumInFile (ZipFile zipFile) throws IOException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_mm_yyyy_hh_MM_ss");
        File hashInfo = new File(zipFile.getFile().getParent() + "\\" + dateFormat.format(new Date()) + ".txt");

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(hashInfo))) {
            bufferedWriter.write(getChecksum(zipFile.getFile().getPath()));
            bufferedWriter.flush();
        }
        return hashInfo;
    }
}
