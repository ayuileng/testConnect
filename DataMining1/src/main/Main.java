package main;

import org.junit.Test;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.*;

/**
 * Created by Atusleng_samA on 2016/9/20.
 */
public class Main {
    private static Set<String> stopwords = new HashSet<>();
     //每篇文章是一个String的list，每个子文件夹是list的list，于是整个语料库是list的list的list
    //private static Map<List<List<String>>> articles = new ArrayList<>();
    private static HashMap<Integer,HashMap<Integer,ArrayList<String>>> articles = new HashMap<>();
    private static List<File[]> dirs = new ArrayList<>();
    public void readStopWords(){
        try {
            System.out.println("加载停止词...");
            String word;
            String dict_filename = "./stopword/stop.txt";
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(dict_filename),"utf-8"));
            while((word = br.readLine())!=null){
                stopwords.add(word);
            }
            System.out.println("停止词加载完毕,共有"+stopwords.size()+"个!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void readTxtWords(){
        try {
            File filepath = new File("./files/");
            dirs = new ArrayList<>();
            for (int i=0;i<filepath.listFiles().length;i++){
                File[] files = filepath.listFiles()[i].listFiles();
                dirs.add(files);
            }

            for(int i=0;i<dirs.size();i++){
                HashMap<Integer, ArrayList<String>> temp1=new HashMap<>();
                for (int j=0;j<dirs.get(i).length;j++){
                    ArrayList<String> temp = new ArrayList<>();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dirs.get(i)[j]), "utf-8"));
                        String line;
                        while((line=reader.readLine())!=null){
                            List<String> words = seprateWords(line);
                            for (String word:
                                 words  ) {
                                if(word.length()>3&&!stopwords.contains(word)){
                                    temp.add(word);

                                    temp1.put(j,temp);
                                    articles.put(i,temp1);
                                }
                            }
                        }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> seprateWords(String line){
        List<String> words=new ArrayList<>();
        try {
            IKSegmenter segmenter =  new IKSegmenter(new StringReader(line),true);
            Lexeme word;
            while((word = segmenter.next())!=null){
                words.add(word.getLexemeText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }
    @Test
    public void test(){
        int sum=0;
        readTxtWords();
        for (Map.Entry e:
             articles.entrySet()) {
            for (Map.Entry e2:
                    ((HashMap<Integer,ArrayList<String>>)e.getValue()).entrySet()) {
                sum+=((ArrayList<String>)e2.getValue()).size();
            }
        }
        System.out.println(sum);
    }
    @Test
    public void test2(){
        List<String> strings = seprateWords("i was a boy");
        for (int i = 0; i < strings.size(); i++) {
            System.out.println(strings.get(i));
        }


    }
}
