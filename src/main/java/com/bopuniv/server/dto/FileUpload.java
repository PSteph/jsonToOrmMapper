package com.bopuniv.server.dto;

public class FileUpload {

    private String logoName;
    private String logoUrl;
    private long fileSize;

    public FileUpload(String logoName, String logoUrl, long fileSize) {
        this.logoName = logoName;
        this.logoUrl = logoUrl;
        this.fileSize = fileSize;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "FileUpload{" +
                "logoName='" + logoName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
