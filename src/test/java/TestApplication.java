import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestApplication {
    @Test
    void canReadInputFile() {
        Files.list(Paths.get("test-files"));
        String path = "test-input.txt";
        byte[] data = Files.readAllBytes(Paths.get(path));

        assertEquals()
    }

    String getFileCheckSum(byte[] data){
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(data);
            return new BigInteger(1, hash).toString(16);
        }catch (Exception e){
            System.out.println(e);
        }
        return "";
    }
}
