import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

public class Main {
    public static String finalAns;
    public static GUI app;
    public static String dir;
    public static List<Path> paths;

    public static void main(String[] args) throws IOException {

//      String currentDir = System.getProperty("user.dir");
        String currentDir = System.getProperty("user.dir");
        dir = "C:\\Users\\Спальня\\Desktop\\qweqw";
        Path path = new File(currentDir).toPath();
        List<Path> paths = new ArrayList<>();
        List<Path> finalPaths = paths;
        Files.walk(path, FOLLOW_LINKS)
                .forEach(file -> {
                    if(file.toFile().isFile() && file.toFile().getPath().endsWith(".P00")){
                        finalPaths.add(file);
                    }
                });
        paths = finalPaths;
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            fileNames.add(paths.get(i).getFileName() + " - " + getCoordSys(paths.get(i)));
        }

        app = new GUI(paths);
        app.dir = dir;
        app.setVisible(true);
    }

    public static String getCoordSys(Path file) throws FileNotFoundException {
        String ans = null;
        Scanner scanner = new Scanner(file.toFile());
        boolean haveAns = false;
        while (!haveAns) {
            String[] wordArray = scanner.nextLine().split(" ");
            for (int i = 0; i < wordArray.length; i++) {
                if (wordArray[i].startsWith("G5")) {
                    ans = wordArray[i];
                    haveAns = true;
                }
            }
        }
        return ans;
    }

    public static void outputWrite(List<Path> keyy, String outputName) throws IOException {
        paths = keyy;
        finalAns = concatFiles(paths);
        FileWriter fstream = new FileWriter(dir + "\\"  + outputName + ".P00");
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(finalAns);
        out.close();
        fstream.close();
        app.setVisible(false);
    }

    public static String concatFiles(List<Path> files) throws IOException {
        String ans = removeEnd(toStr(files.get(0)));
        String lastFile = removeStart(toStr(files.get(files.size() - 1)));
        for (int i = 1; i < files.size() - 1; i++) {
            ans = ans + (removeEnd(removeStart(toStr(files.get(i)))));
        }
        ans = ans + (lastFile);
        return ans;
    }

    public static String removeEnd(String file) {
        String[] lines = file.split("\n");
        String[] removeEndLines = new String[lines.length - 2];
        for (int i = 0; i < lines.length - 2; i++) {
            removeEndLines[i] = lines[i];
        }
        return String.join("\n", removeEndLines);
    }

    public static String removeStart(String file) {
        String[] lines = file.split("\n");
        String[] removeStartLines = new String[lines.length - 6];
        for (int i = 6; i < lines.length; i++) {
            removeStartLines[i - 6] = lines[i];
        }
        return String.join("\n", removeStartLines);
    }

    public static String toStr(Path path) throws IOException {

        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(path)), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {}

        String fileContent = contentBuilder.toString();
        return fileContent;

        //String result = Files.readString(path);
        //return result;
    }
}
