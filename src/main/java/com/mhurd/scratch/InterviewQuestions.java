package com.mhurd.scratch;

import java.io.File;

public class InterviewQuestions {

    private static class PrintDirs {

        private static void printDirRecur(int indent, File startFile) {
            for (int i = 0; i < indent; i++) {
                System.out.print("-");
            }
            System.out.println(" " + startFile.getName());
            if (startFile.isDirectory()) {
                File[] files = startFile.listFiles();
                if (files != null) {
                    for (File file : files) {
                        printDirRecur(indent + 4, file);
                    }
                }
            }
        }

        static void printDirs(String pathName) {
            File start = new File(pathName);
            printDirRecur(1, start);
        }

    }

    private static class FindFile {

        private static String findRecur(String searchString, File candidate) {
            System.out.println("Searching: " + candidate.getAbsolutePath());
            if (searchString.equals(candidate.getName())) {
                return candidate.getAbsolutePath();
            } else {
                if (candidate.isDirectory()) {
                    File[] files = candidate.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            String path = findRecur(searchString, file);
                            if (path != null) {
                                return path;
                            }
                        }
                    }
                }
            }
            return null;
        }

        static String find(String filename) {
            File start = new File("C:/Users/mhurd/development");
            return findRecur(filename, start);
        }

    }

    public static void main(String[] args) {
        String filename = "config.ini";
        System.out.println("Finding file: " + filename);
        String location = FindFile.find(filename);
        System.out.println("\nFile location: " +  location);
        System.out.println("Printing Dir: " + filename);
        PrintDirs.printDirs(".");
    }

}
