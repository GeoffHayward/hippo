package uk.nhs.digital.test.util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class FileUtils {

    public static String fileContentFromClasspath(final String fileClasspathPath) {
        try {

            return StreamUtils.copyToString(
                FileUtils.class.getResourceAsStream(fileClasspathPath), StandardCharsets.UTF_8
            );

        } catch (final IOException e) {
            throw new UncheckedIOException("Failed to read test content file from " + fileClasspathPath, e);
        }
    }

    public static String fileContentFromFilesystem(final String fileSystemPath) {
        try {
            return org.apache.commons.io.FileUtils.readFileToString(
                Paths.get(fileSystemPath).toFile(), StandardCharsets.UTF_8
            );
        } catch (final IOException e) {
            throw new UncheckedIOException("Failed to read test content file from " + fileSystemPath, e);
        }
    }
}