package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.*;

/*
Проход по дереву файлов
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        ArrayList<File> list = new ArrayList<File>();
        File path = new File(args[0]);
        File resultFileAbsolutePath = new File(args[1]);
        File newFileName = new File(resultFileAbsolutePath.getParent() + "/allFilesContent.txt");
        FileUtils.renameFile(resultFileAbsolutePath,newFileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName));
        for (File file:searchFiles(path, list)) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()){
                writer.write(reader.readLine());
            }
            writer.newLine();
            reader.close();
        }
        writer.close();
    }
    static public ArrayList<File> searchFiles(File folder, ArrayList<File> list)
    {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries)
        {
            if (entry.isDirectory())
            {
                searchFiles(entry, list);
            }
            else if(entry.isFile()){
                if(entry.length()>50){
                    FileUtils.deleteFile(entry);
                }else{
                    list.add(entry);
                }
            }
        }
        Collections.sort(list, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                String fileName1 = o1.getName();
                String fileName2 = o2.getName();
                return fileName1.compareTo(fileName2);
            }
        });
        return list;
    }
}
