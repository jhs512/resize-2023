package com.sbs.resize.standard.util;

import org.apache.tika.Tika;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Ut {

    public static class date {
        public static String getCurrentDateFormatted(String pattern) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(new Date());
        }
    }

    public static class file {
        private static String getFileExt(File file) {
            Tika tika = new Tika();
            String mimeType = "";

            try {
                mimeType = tika.detect(file);
            } catch (IOException e) {
                return null;
            }

            String ext = mimeType.replaceAll("image/", "");
            ext = ext.replaceAll("jpeg", "jpg");

            return ext.toLowerCase();
        }

        public static String getFileExtTypeCodeFromFileExt(String ext) {
            switch (ext) {
                case "jpeg":
                case "jpg":
                case "gif":
                case "png":
                    return "img";
                case "mp4":
                case "avi":
                case "mov":
                    return "video";
                case "mp3":
                    return "audio";
            }

            return "etc";
        }

        public static String getFileExtType2CodeFromFileExt(String ext) {

            switch (ext) {
                case "jpeg":
                case "jpg":
                    return "jpg";
                case "gif", "png", "mp4", "mov", "avi", "mp3":
                    return ext;
            }

            return "etc";
        }

        public static String getFileExt(String fileName) {
            int pos = fileName.lastIndexOf(".");

            if (pos == -1) {
                return "";
            }

            String ext = fileName.substring(pos + 1).trim();

            return ext;
        }

        public static String getFileNameFromUrl(String fileUrl) {
            try {
                return Paths.get(new URI(fileUrl).getPath()).getFileName().toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return "";
            }
        }

        public static String downloadFileByHttp(String fileUrl, String outputDir) {
            String originFileName = getFileNameFromUrl(fileUrl);
            String fileExt = getFileExt(originFileName);

            if (fileExt.length() == 0) {
                fileExt = "tmp";
            }

            String tempFileName = UUID.randomUUID() + "." + fileExt;
            String filePath = outputDir + "/" + tempFileName;

            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(fileUrl).openStream());
                FileChannel fileChannel = fileOutputStream.getChannel();
                fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            } catch (Exception e) {
                throw new DownloadFileFailException();
            }

            File file = new File(filePath);

            if (file.length() == 0) {
                throw new DownloadFileFailException();
            }

            if (fileExt.equals("tmp")) {
                String ext = getFileExt(file);

                if (ext == null || ext.length() == 0) {
                    throw new DownloadFileFailException();
                }

                String newFilePath = filePath.replaceAll("\\.tmp", "\\." + ext);
                moveFile(filePath, newFilePath);
                filePath = newFilePath;
            }

            return filePath;
        }

        public static void moveFile(String filePath, String destFilePath) {
            Path file = Paths.get(filePath);
            Path destFile = Paths.get(destFilePath);

            try {
                Files.move(file, destFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void delete(String path) {
            Path file = Paths.get(path);

            try {
                Files.delete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static class DownloadFileFailException extends RuntimeException {

        }
    }
}
