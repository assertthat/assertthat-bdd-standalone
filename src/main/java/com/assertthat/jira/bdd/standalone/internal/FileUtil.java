package com.assertthat.jira.bdd.standalone.internal;

import org.codehaus.plexus.util.DirectoryScanner;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Copyright (c) 2018 AssertThat
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p>
 * Created by Glib_Briia on 15/05/2018.
 */
public class FileUtil {

    private final static String DEFAULT_FILE_INCLUDE_PATTERN = "**/*.json";

    public String[] findJsonFiles(File targetDirectory, String fileIncludePattern, String fileExcludePattern) {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(targetDirectory);

        if (null == fileIncludePattern) {
            scanner.setIncludes(new String[]{DEFAULT_FILE_INCLUDE_PATTERN});
        } else {
            scanner.setIncludes(new String[]{fileIncludePattern});
        }
        if (null != fileExcludePattern) {
            scanner.setExcludes(new String[]{fileExcludePattern});
        }
        scanner.setBasedir(targetDirectory);
        scanner.scan();
        return scanner.getIncludedFiles();
    }

    public File unpackArchive(File theFile, File targetDir) throws IOException {
        if (!theFile.exists()) {
            throw new IOException(theFile.getAbsolutePath() + " does not exist");
        }
        if (!buildDirectory(targetDir)) {
            throw new IOException("Could not create directory: " + targetDir);
        }
        ZipFile zipFile = new ZipFile(theFile);
        for (Enumeration entries = zipFile.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            File file = new File(targetDir, File.separator + entry.getName());
            if (!buildDirectory(file.getParentFile())) {
                throw new IOException("Could not create directory: " + file.getParentFile());
            }
            if (!entry.isDirectory()) {
                copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
            } else {
                if (!buildDirectory(file)) {
                    throw new IOException("Could not create directory: " + file);
                }
            }
        }
        zipFile.close();
        return theFile;
    }


    public void copyInputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        while (len >= 0) {
            out.write(buffer, 0, len);
            len = in.read(buffer);
        }
        in.close();
        out.close();
    }

    public boolean buildDirectory(File file) {
        return file.exists() || file.mkdirs();
    }
}
