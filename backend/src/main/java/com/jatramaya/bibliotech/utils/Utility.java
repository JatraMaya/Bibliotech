package com.jatramaya.bibliotech.utils;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

public class Utility {

    public static boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }

    public static boolean hasValue(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean hasValue(MultipartFile file) {
        return file != null && !file.isEmpty();
    }
}
