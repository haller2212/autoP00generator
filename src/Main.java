import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
//      C:\Users\jurii\IdeaProjects\swingTest\out\artifacts\swingTest_jar
//      C:\Users\jurii\OneDrive\Рабочий стол\qweqq
public class Main {
    public static String finalAns;
    public static String dir;
    public static List<Path> paths;

    public static void main(String[] args) throws IOException {

//      String currentDir = System.getProperty("user.dir");

        dir = System.getProperty("user.dir");
        Path path = Path.of(dir);
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

        SimpleGUI app = new SimpleGUI(paths);
        app.dir = dir;
        app.setVisible(true);
    }

    public static void outputWrite(List<Path>  keyy, String outputName) throws IOException {
        paths = keyy;
        finalAns = concatFiles(paths);
        FileWriter fstream = new FileWriter(dir + "\\"  + outputName + ".P00");
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(finalAns);
        out.close();
        fstream.close();
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

    public static String removeStart(String file) {
        String[] lines = file.split("\n");
        String[] removeStartLines = new String[lines.length - 6];
        for (int i = 6; i < lines.length; i++) {
            removeStartLines[i - 6] = lines[i];
        }
        return String.join("\n", removeStartLines);
    }

    public static String removeEnd(String file) {
        String[] lines = file.split("\n");
        String[] removeEndLines = new String[lines.length - 2];
        for (int i = 0; i < lines.length - 2; i++) {
            removeEndLines[i] = lines[i];
        }
        return String.join("\n", removeEndLines);
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

    public static String toStr(Path path) throws IOException {
        String result = Files.readString(path);
        return result;
    }

    public static List<Path> sortByStr(List<Path> paths, String key) {
        String result = key.replaceAll("[^0-9]", "");
        char[] chars = result.toCharArray();
        String[] keys = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            keys[i] = String.valueOf(chars[i]);
        }
        ArrayList<Path> ap = (ArrayList<Path>) paths;
        List<Path> ans = (List<Path>) ap.clone();


        int[] keys1 = Arrays.stream(keys)
                .mapToInt(Integer::parseInt)
                .toArray();

        for (int i = 0; i < keys1.length; i++) {
            keys1[i]--;
        }

        for (int i = 0; i < paths.size(); i++) {
            ans.set(i, paths.get(keys1[i]));
        }
        return ans;
    }
}
