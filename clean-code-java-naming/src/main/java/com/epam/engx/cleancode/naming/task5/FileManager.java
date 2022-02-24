package com.epam.engx.cleancode.naming.task5;

import com.epam.engx.cleancode.naming.task5.predicates.FileExtensionPredicate;
import com.epam.engx.cleancode.naming.task5.thirdpartyjar.InvalidDirectoryException;
import com.epam.engx.cleancode.naming.task5.thirdpartyjar.InvalidFileTypeException;
import com.epam.engx.cleancode.naming.task5.thirdpartyjar.PropertyUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public final class FileManager {

    private static final String[] IMAGE_EXTENSIONS = {"jpg", "png"};
    private static final String[] DOCUMENT_EXTENSIONS = {"pdf", "doc"};

    private String basePath = PropertyUtil.loadProperty("basePath");

    public File retrieveFile(String fileName) {
        validateFileType(fileName);
        final String directoryPath = basePath + File.separator;
        return Paths.get(directoryPath, fileName).toFile();
    }

    public List<String> listAllImages() {
        return listingFilesWithExtensions(basePath, IMAGE_EXTENSIONS);
    }

    public List<String> listAllDocumentFiles() {
        return listingFilesWithExtensions(basePath, DOCUMENT_EXTENSIONS);
    }

    private void validateFileType(String fileName) {
        if (isInvalidFileType(fileName)) {
            throw new InvalidFileTypeException("File type not Supported: " + fileName);
        }
    }

    private boolean isInvalidFileType(String fileName) {
        return isInvalidImage(fileName) && isInvalidDocument(fileName);
    }

    private boolean isInvalidImage(String fileName) {
        FileExtensionPredicate imageExtensionsPredicate = new FileExtensionPredicate(IMAGE_EXTENSIONS);
        return !imageExtensionsPredicate.test(fileName);
    }

    private boolean isInvalidDocument(String fileName) {
        FileExtensionPredicate documentExtensionsPredicate = new FileExtensionPredicate(DOCUMENT_EXTENSIONS);
        return !documentExtensionsPredicate.test(fileName);
    }

    private List<String> listingFilesWithExtensions(String directoryPath, String[] allowedExtensions) {
        final FileExtensionPredicate predicate = new FileExtensionPredicate(allowedExtensions);
        return Arrays.asList(directory(directoryPath).list(getFilenameFilterByPredicate(predicate)));
    }

    private FilenameFilter getFilenameFilterByPredicate(final FileExtensionPredicate predicate) {
        return new FilenameFilter() {
            @Override
            public boolean accept(File directory, String string) {
                return predicate.test(string);
            }
        };
    }

    private File directory(String directoryPath) {
        File directory = new File(directoryPath);
        validateDirectory(directory);
        return directory;
    }

    private void validateDirectory(File directoryInstance) {
        if (isNotDirectory(directoryInstance)) {
            throw new InvalidDirectoryException("Invalid directory found: " + directoryInstance.getAbsolutePath());
        }
    }

    private boolean isNotDirectory(File directory) {
        return !directory.isDirectory();
    }

}