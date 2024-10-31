package com.arius.qrmenu.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


public class ByteToMultipartFileConverter {

    public static MultipartFile convert(byte[] bytes, String fileName) throws IOException {

    	final String[] contentType = new String[1];
        contentType[0] = URLConnection.guessContentTypeFromName(fileName);
        if (contentType[0] == null) {
            // Nếu loại MIME không thể xác định, sử dụng giá trị mặc định
            contentType[0] = "application/octet-stream";
        }
        return new MultipartFile() {
        	
            @Override
            public String getName() {
                return fileName;
            }

            @Override
            public String getOriginalFilename() {
                return StringUtils.cleanPath(fileName);
            }

            @Override
            public String getContentType() {
                // Giả sử kiểu MIME là ứng dụng/octet-stream
                return contentType[0];
            }

            @Override
            public boolean isEmpty() {
                return bytes == null || bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
            	 Path path = Paths.get(dest.getPath());
            	 Files.write(path, bytes);
            }
        };
    }
}
