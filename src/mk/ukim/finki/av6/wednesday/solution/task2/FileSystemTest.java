package mk.ukim.finki.av6.wednesday.solution.task2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class FileNameExistsException extends Exception {
    public FileNameExistsException(String fileName, String folderName) {
        super(String.format("There is already a file named %s in the folder %s", fileName, folderName));
    }
}

class IndentPrinter {
    public static String printIndentation(int level) {
        return IntStream.range(0, level)
                .mapToObj(i -> "\t")
                .collect(Collectors.joining());
    }
}

interface IFile extends Comparable<IFile> {

    String getFileName();

    long getFileSize();

    String getFileInfo(int indent);

    void sortBySize();

    long findLargestFile();
}

class File implements IFile {

    protected String fileName;
    protected long fileSize;

    public File(String fileName, long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public File(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public long getFileSize() {
        return this.fileSize;
    }

    @Override
    public String getFileInfo(int indent) {
        return String.format("%sFile name: %10s File size: %10d\n",
                IndentPrinter.printIndentation(indent), getFileName(), getFileSize());
    }

    @Override
    public void sortBySize() {
        return;
    }

    @Override
    public long findLargestFile() {
        return this.fileSize;
    }

    @Override
    public int compareTo(IFile other) {
        return Long.compare(this.fileSize, other.getFileSize());
    }
}

class Folder extends File implements IFile {

    private List<IFile> files;

    public Folder(String fileName) {
        super(fileName);
        files = new ArrayList<>();
    }

    private boolean ifNameExists(String fileName) {
        return files.stream()
                .map(IFile::getFileName)
                .anyMatch(name -> name.equals(fileName));
    }

    public void addFile(IFile file) throws FileNameExistsException {
        if (ifNameExists(file.getFileName()))
            throw new FileNameExistsException(file.getFileName(), this.fileName);

        files.add(file);
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public long getFileSize() {
        return files.stream().mapToLong(IFile::getFileSize).sum();
    }

    @Override
    public String getFileInfo(int indent) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%sFolder name: %10s Folder size: %10d\n",
                IndentPrinter.printIndentation(indent), getFileName(), getFileSize()));

        files.stream().forEach(file -> stringBuilder.append(file.getFileInfo(indent + 1)));
        return stringBuilder.toString();
    }

    @Override
    public void sortBySize() {
        Comparator<IFile> comparator = Comparator.comparingLong(IFile::getFileSize);
        files.sort(comparator);
        files.forEach(IFile::sortBySize);
    }

    @Override
    public long findLargestFile() {
        OptionalLong largest = files.stream().mapToLong(IFile::findLargestFile).max();
        if (largest.isPresent())
            return largest.getAsLong();
        else return 0L;
    }
}

class FileSystem {
    private Folder root;

    public FileSystem() {
        root = new Folder("root");
    }

    public void addFile(IFile file) throws FileNameExistsException {
        root.addFile(file);
    }

    public long findLargestFile() {
        return root.findLargestFile();
    }

    public void sortBySize() {
        root.sortBySize();
    }

    @Override
    public String toString() {
        return this.root.getFileInfo(0);
    }
}

public class FileSystemTest {

    public static Folder readFolder(Scanner sc) {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < totalFiles; i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String[] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return folder;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());
    }
}

