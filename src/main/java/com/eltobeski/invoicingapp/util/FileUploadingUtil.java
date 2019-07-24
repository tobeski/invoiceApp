package com.eltobeski.invoicingapp.util;

import com.eltobeski.invoicingapp.exception.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class FileUploadingUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadingUtil.class);

    private FileUploadingUtil() {
    }

    public static String uploadMultipartFiles(String uploadPath, MultipartFile multipartFile) throws FileUploadException {
        return upload(multipartFile, uploadPath);
    }

    public static List<String> uploadMultipartFiles(List<MultipartFile> multipartFiles, String uploadPath) throws FileUploadException {
        List<String> filesPath = new ArrayList();
        Iterator iterator = multipartFiles.iterator();

        while(iterator.hasNext()) {
            MultipartFile file = (MultipartFile)iterator.next();
            filesPath.add(upload(file, uploadPath));
        }

        return filesPath;
    }

    private static String upload(MultipartFile file, String uploadPath) throws FileUploadException {
        LOGGER.info(String.format("Uploading File, %s with size of %s and content type of %s", file.getOriginalFilename(), file.getSize(), file.getContentType()));
        String filePath = null;
        if (file != null && !file.isEmpty()) {
            try {
                String name = file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty() ? file.getOriginalFilename() : file.getName();
                byte[] bytes = file.getBytes();
                filePath = String.format("%s%s%s", uploadPath, File.separator, name.replace(" ", "-"));
                File uploadedFile = new File(filePath);
                if (!uploadedFile.exists()) {
                    uploadedFile.getParentFile().mkdirs();
                    uploadedFile.createNewFile();
                }

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                Throwable throwable = null;

                try {
                    stream.write(bytes);
                    stream.close();
                } catch (Throwable throwable1) {
                    throwable = throwable1;
                    throw throwable1;
                } finally {
                    if (stream != null) {
                        if (throwable != null) {
                            try {
                                stream.close();
                            } catch (Throwable throwable1) {
                                throwable.addSuppressed(throwable1);
                            }
                        } else {
                            stream.close();
                        }
                    }

                }
            } catch (Exception var19) {
                throw new FileUploadException("Error While Uploading File", var19);
            }
        }

        return filePath;
    }
}