package com.news.storage;

import java.io.InputStream;

public interface StorageService {

    String upload(String prefix, String filename, InputStream inputStream);

    InputStream download(String prefix, String filename);
}
