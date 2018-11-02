package com.aplusstory.fixme;

public interface FootprintDataManager extends UserDataManager{
    @Override
    int setFileManager(FileManager f);
    int setLocationDataManager(LocationDataManager l);
}
