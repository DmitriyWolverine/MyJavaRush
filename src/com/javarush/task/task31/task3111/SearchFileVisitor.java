package com.javarush.task.task31.task3111;

import java.nio.file.*;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;


public class SearchFileVisitor extends SimpleFileVisitor<Path> {
    private String partOfName;
    private String partOfContent;
    private int minSize;
    private int maxSize;
    private List<Path> foundFiles  = new ArrayList<>();

    public List<Path> getFoundFiles() {
        return foundFiles;
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file); // размер файла: content.length

       boolean containsContent = true;
       boolean containsName = true;
       boolean isInMinBorder = true;
       boolean isInMaxBorder = true;
       if(partOfName!=null)
             containsName = (file.getFileName().toString().contains(partOfName ));
       if (partOfContent!=null)
             containsContent =  (Files.lines(file).anyMatch(string -> string.matches(".*" + partOfContent + ".*")));
       if(minSize!=0)
         isInMinBorder = (minSize < content.length);
       if (maxSize!=0)
           isInMaxBorder = (maxSize > content.length);

       if(containsName && containsContent  && isInMaxBorder && isInMinBorder){
           foundFiles.add(file);
       }
        return super.visitFile(file, attrs);
    }
}
