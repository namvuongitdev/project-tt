package com.example.finally1.util.file;

import com.example.finally1.execption.custom.FileNotFound;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Component
public class Image {

    public String upload(MultipartFile file) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String fileName = file.getOriginalFilename();

        if (file.isEmpty()) {
            throw new FileNotFound("handleFileIsEmpty");
        } else if (fileName != null && !fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
            throw new FileNotFound("handleFileType");
        }

        File newFile = new File("D:/SpringExamples/finally-1/img/" + fileName);
        try {
            inputStream = file.getInputStream();

            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            return fileName;
        } catch (Exception e) {
            System.out.println("ErrorAddGrammar:" + e);
            return null;
        }
    }
}
