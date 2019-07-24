package com.eltobeski.invoicingapp.model;

import org.springframework.web.multipart.MultipartFile;

public class FileRequest {
    MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
