package ru.aldonin.archiver;

import de.idyl.winzipaes.AesZipFileEncrypter;
import de.idyl.winzipaes.impl.AESEncrypterBC;

import java.io.File;
import java.io.IOException;

public class ZipEncrypter {

    public static void encryptTargetFile (String zipPath, String password) throws IOException {
        File aNewZipFile = new File("src.zip");
        File existingUnzippedFile = new File("src.txt");

        AESEncrypterBC encrypter = new AESEncrypterBC();
        encrypter.init("password", 0);  // The 0 is keySize, it is ignored for AESEncrypterBC

        AesZipFileEncrypter zipEncrypter = new AesZipFileEncrypter(aNewZipFile, encrypter);

        zipEncrypter.add(existingUnzippedFile, "src.txt", "password");
        zipEncrypter.close();
    }
}
