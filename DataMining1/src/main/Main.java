package main;

import org.junit.Test;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Atusleng_samA on 2016/9/20.
 */
public class Main {
    private static Set<String> stopwords = new HashSet<>();
    private static List<File[]> dirs = new ArrayList<>();
    private static Map<String,Integer> article = new TreeMap<>();//一篇文章
    private static List<TreeMap<String,Integer>> articles = new ArrayList<>();//一个文件夹
    private static Map<String,Integer> times = new HashMap<>();
    private static TreeMap<String,HashMap<String,Integer>> TF = new TreeMap<>();
    /**
     * 读取stopwords到一个集合中
     */
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
            throw new RuntimeException("停止词加载失败");
        }
    }

    public void readAllFiles(){
        try {
            File filepath = new File("./files/");
            dirs = new ArrayList<>();
            for (int i=0;i<filepath.listFiles().length;i++){
                File[] files = filepath.listFiles()[i].listFiles();
                dirs.add(files);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Map<String,Integer> readTFofOneArticle(File file){
        Map<String,Integer> OneArticle = new HashMap<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            while ((line = reader.readLine()) != null) {
                List<String> words = seprateWords(line);
                for (String word : words) {
                    if (word.length() > 3 && !stopwords.contains(word) && isWord(word)) {
                        if(times.keySet().contains(word)){
                            times.put(word,times.get(word)+1);
                        }else{
                            times.put(word,1);
                        }
                        if(OneArticle.keySet().contains(word)){
                            OneArticle.put(word,OneArticle.get(word)+1);
                        }else{
                            OneArticle.put(word,1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("读取一篇文章出错");
        }
        return OneArticle;
    }
    /**
     * 将句子分词
     * @param line
     * @return
     */
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

    /**
     * 一共多少个文档：580
     * @return
     */
    public int N() {
        int sum = 0;
        for (File[] fs :
                dirs) {
            sum+=fs.length;
        }
        return sum;
    }

    public int totalWords(Map<String,Integer> oneArticle){
        int sum = 0;
        for (Map.Entry<String,Integer> e:
             oneArticle.entrySet()) {
            sum+=e.getValue();
        }
        return sum;
    }
    public TreeMap<String,Double> getTF(Map<String,Integer> oneArticle){
        int sum = totalWords(oneArticle);
        TreeMap<String,Double> TF=new TreeMap<>();
        for (Map.Entry<String,Integer> e :
                oneArticle.entrySet()) {
            TF.put(e.getKey(), 1+Math.log(e.getValue()));
        }
        return TF;
    }

    /**
     * 判断是否是一个英文单词
     * @param word
     * @return
     */
    public boolean isWord(String word){
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }
    public int nTimes(List<TreeMap<String, Double>> TFs,String word){
        int t=0;
        for (TreeMap<String, Double> tf:
             TFs) {
            if(tf.keySet().contains(word)){
                t++;
            }
        }
        return t;
    }

    @Test
    public void test(){
        readStopWords();
        readAllFiles();
        List<TreeMap<String, Double>> TFs = new ArrayList<>();
        for (File[] fs :
                dirs) {
            for (File file :
                    fs) {
                Map<String, Integer> article = readTFofOneArticle(file);
                TreeMap<String, Double> tf = getTF(article);
                TFs.add(tf);
            }
        }


//        for(String key:tf.keySet()){
//            d+=tf.get(key);
//            System.out.println("word:"+key+"的TF值是："+tf.get(key));
//        }
//        System.out.println(d);

    }

    @Test
    public void test2(){
        readAllFiles();
        for (File[] fs :
                dirs) {
            for (File file :
                    fs) {
                readTFofOneArticle(file);
            }
        }
//        for (Map.Entry e:
//        times.entrySet()) {
//            System.out.println(e.getKey()+" : "+e.getValue());
//        }
//        System.out.println(times.get("iloveyajima"));
    }
    @Test
    public void test3(){
        readStopWords();
        readAllFiles();
        Set<String> wordSet = new HashSet<>();
        for (File[] fs :
                dirs) {
            for (File file :
                    fs) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        List<String> words = seprateWords(line);
                        for (String word :
                                words) {
                            if (word.length() > 3 && (!stopwords.contains(word)) && isWord(word)) {
                                wordSet.add(word);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("读取一篇文章出错");
                }
            }
        }
        System.out.println(wordSet.size());
    }
}
