package com.aplusstory.fixme;

import org.jetbrains.annotations.Nullable;

public interface FileManager {
    @Nullable
    String getData(String key);
    boolean setData(String key, String value);
}
