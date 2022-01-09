package com.example.filestorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class FileStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileStorageApplication.class, args);
		/*SecretKey key = null;
		KeyGenerator keyGenerator = null;
		try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\hflbt\\Desktop\\key")) {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			key = keyGenerator.generateKey();
			fileOutputStream.write(key.getEncoded());
			fileOutputStream.flush();
		} catch(Exception e) {
			e.printStackTrace();
		}

		try {
			byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\hflbt\\Desktop\\key"));
			SecretKey key2 = new SecretKeySpec(bytes, "AES");
			System.out.println(key2.equals(key));
		} catch(Exception e) {
			e.printStackTrace();
		}*/
	}

}
