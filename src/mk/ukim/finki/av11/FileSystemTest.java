package mk.ukim.finki.av11;

import java.util.ArrayList;
import java.util.List;

interface IFile {
    String getFileName();
    long getFileSize();
    String getFileInfo(int indent);
    void sortBySize();
    long findLargestFile();
}

class File implements IFile{ //obichna datoteka
    String name;
    long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(int indent) {
        StringBuilder indentString = new StringBuilder();
        for (int i=0;i<indent;i++){
            indentString.append("\t");
        }
        return String.format("%sFile name %10s File size: %10d", indentString.toString(), name, size);
    }

    @Override
    public void sortBySize() {
        //DO NOTHING
    }

    @Override
    public long findLargestFile() {
        return size;
    }
}

class Folder implements IFile {

    String name;
    List<IFile> children;

    public Folder(String name) {
        this.name = name;
        children = new ArrayList<>();
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return children.stream().mapToLong(IFile::getFileSize).sum();
    }

    @Override
    public String getFileInfo(int indent) {
        StringBuilder indentString = new StringBuilder();
        for (int i=0;i<indent;i++){
            indentString.append("\t");
        }
        StringBuilder finalSb = new StringBuilder();

        finalSb.append(String.format("%sFile name %10s File size: %10d", indentString.toString(), name, getFileSize()));
        children.forEach(child -> finalSb.append(child.getFileInfo(indent+1)));
        return finalSb.toString();
    }

    @Override
    public void sortBySize() {

    }

    @Override
    public long findLargestFile() {
        return 0;
    }

    public void addFile(IFile file) {
        children.add(file);
    }
}

public class FileSystemTest {

//    public static Folder readFolder (Scanner sc)  {
//
//        Folder folder = new Folder(sc.nextLine());
//        int totalFiles = Integer.parseInt(sc.nextLine());
//
//        for (int i=0;i<totalFiles;i++) {
//            String line = sc.nextLine();
//
//            if (line.startsWith("0")) {
//                String fileInfo = sc.nextLine();
//                String [] parts = fileInfo.split("\\s+");
//                try {
//                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
//                } catch (FileNameExistsException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//            else {
//                try {
//                    folder.addFile(readFolder(sc));
//                } catch (FileNameExistsException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }
//
//        return folder;
//    }

//    public static void main(String[] args)  {
//
//        //file reading from input
//
//        Scanner sc = new Scanner (System.in);
//
//        System.out.println("===READING FILES FROM INPUT===");
//        FileSystem fileSystem = new FileSystem();
//        try {
//            fileSystem.addFile(readFolder(sc));
//        } catch (FileNameExistsException e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println("===PRINTING FILE SYSTEM INFO===");
//        System.out.println(fileSystem.toString());
//
//        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
//        fileSystem.sortBySize();
//        System.out.println(fileSystem.toString());
//
//        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
//        System.out.println(fileSystem.findLargestFile());
//
//
//
//
//    }
}
