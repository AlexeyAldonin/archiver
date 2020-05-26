package ru.aldonin.archiver;

import net.lingala.zip4j.ZipFile;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.security.MessageDigest.getInstance;
import static org.junit.Assert.*;

public class CheckSumUtilsTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void getChecksum() throws NoSuchAlgorithmException, IOException {

        File file = temporaryFolder.newFile("test.zip");

        String expected = CheckSumUtils.getChecksum(file.getPath());

        MessageDigest md = getInstance("SHA-1");
        DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(file), md);
        while (digestInputStream.read() != -1) ;//empty loop to clear the data
        md = digestInputStream.getMessageDigest();
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        String actual = result.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void createCheckSumInFile() throws IOException {

        File file = temporaryFolder.newFile("test.zip");
        ZipFile test = new ZipFile(file);
        File sumContainer = CheckSumUtils.createCheckSumInFile(test);
        assertTrue(sumContainer.exists());
    }
}