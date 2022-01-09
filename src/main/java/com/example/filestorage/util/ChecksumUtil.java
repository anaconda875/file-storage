package com.example.filestorage.util;

import org.apache.tomcat.util.buf.HexUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumUtil {

    private ChecksumUtil() {}

    public static String getChecksum(Serializable serializable) throws IOException, NoSuchAlgorithmException {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(serializable);
            MessageDigest md = MessageDigest.getInstance("MD5");

            return HexUtils.toHexString(md.digest(baos.toByteArray()));
        }
    }

}
