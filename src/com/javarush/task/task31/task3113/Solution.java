package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/* 
Что внутри папки?
*/
public class Solution {
    static int numberOfFolders=0;
    static int numberOfFiles=0;
    static long realSize=0;

    public static void main(String[] args) throws IOException {
        BufferedReader conReader = new BufferedReader(new InputStreamReader(System.in));
        String pathToFolder = conReader.readLine();
        conReader.close();

        Path folder =Paths.get ( pathToFolder );
        if( !Files.isDirectory(folder) ){
            System.out.println(folder.toAbsolutePath().toString()+" - не папка");
            return;
        }
        else {

            Files.walkFileTree(folder,new Visitor());

            System.out.println("Всего папок - "+(numberOfFolders-1) );
            System.out.println("Всего файлов - "+numberOfFiles);
            System.out.println("Общий размер - "+realSize);
        }
    }
private  static class Visitor extends SimpleFileVisitor<Path>{
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

        if( attrs.isDirectory() )
        {
            numberOfFolders++;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        numberOfFiles++;
        realSize += attrs.size();
        return FileVisitResult.CONTINUE;
    }
    }
}
