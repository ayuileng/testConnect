package main;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Atusleng_samA on 2016/10/12.
 */
public class Main {
    /**
     * 读取文件信息
     *
     * @return
     */
    private static HashMap<String, String> readFile() {
        File file = new File("D:\\IDEAprojects\\NLP作业2\\file\\ce（ms-word）.txt");
        BufferedReader reader = null;
        InputStreamReader isr;
        HashMap<String, String> words = new HashMap<>();
        try {
            isr = new InputStreamReader(new FileInputStream(file), "utf-8");
            reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                words.put(split[0], split[1]);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            return words;

        }
    }


    public static void main(String[] args) {
        String sb = (new Scanner(System.in)).next();
        HashMap<String, String> words = readFile();
        ArrayList<String> spliteWords = new ArrayList<>();
        for (int i = 0; i < sb.length(); i++) {
            if (words.keySet().contains(sb)) {
                spliteWords.add(sb);
                break;
            }
            String word = sb.substring(0, sb.length() - i);
            if (words.keySet().contains(word)) {
                spliteWords.add(word);
                sb = sb.substring(sb.length() - i);
                if (sb.length() == 1) {
                    spliteWords.add(sb);
                }
                i = 0;
            }
        }
        for (String w :
                spliteWords) {
            System.out.println(w);
        }
    }

}
