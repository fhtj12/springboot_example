package com.example.web.util;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class FileUtil {

    public static List<File> parseMultipartRequestToFile(MultipartHttpServletRequest request) throws IOException {
        List<File> items = new ArrayList<>();

        Iterator<String> fileIterator = request.getFileNames();
        while (fileIterator.hasNext()) {
            List<MultipartFile> fileList = request.getFiles(fileIterator.next());
            for (MultipartFile multipartFile : fileList) {
                File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                multipartFile.transferTo(file);
                items.add(file);
            }
        }

        return items;
    }

    public static List<FileItem> parseMultipartRequestToFileItem(MultipartHttpServletRequest request) {
        List<FileItem> items = new ArrayList<>();

        Iterator<String> fileIterator = request.getFileNames();
        while (fileIterator.hasNext()) {
            List<MultipartFile> fileList = request.getFiles(fileIterator.next());
            for (MultipartFile multipartFile : fileList) {
                CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartFile;
                items.add(commonsMultipartFile.getFileItem());
            }
        }

        return items;
    }

}
