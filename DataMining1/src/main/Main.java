package main;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yajima on 2016/9/24.
 */
public class Main {
    private static Set<String> stopwords = new HashSet<>();//stopword集合
    private static List<ArrayList<File> > dirs = new ArrayList<>();//所有txt对应的File对象
    private static HashSet<String> allWords = new HashSet<>();//所有的词
    private static ArrayList<TreeMap<String,Double>> allTXT = new ArrayList<>();//其中每一项对应的是一个文档的词频数据
    private static TreeMap<String,Double> wordOccurTimes = new TreeMap<>();//每个单词总共出现在多少篇文章中
    private static ArrayList<TreeMap<String,Double>> TF_IDF = new ArrayList<>();//最终的结果矩阵

    //加载stopwords
    private static void readStopWords(){
        System.out.println("加载停止词...");
        String word;
        String dict_filename = "./stopword/stop.txt";
        try {
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

    //读取所有files
    private static void readAllFiles(){
        try {
            File filepath = new File("./files/");

            for (int i=0;i<filepath.listFiles().length;i++){
                File[] files = filepath.listFiles()[i].listFiles();
                ArrayList<File> fs = new ArrayList<>();
                Collections.addAll(fs,files);
                dirs.add(fs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("读取全部文件成功！");
    }

    //逐个txt进行读取，读取的过程中:
    // 1.首先将所有词存入一个set中，然后对于每个单独的txt，统计一下词频，将所有文章的词频存入Array中
    // 2.统计出现一个词的文章共有多少篇
    private static void processEachFile() {
        String line;
        for (ArrayList<File> fs :
                dirs) {
            for (File file :
                    fs) {
                TreeMap<String,Double> oneTXT = new TreeMap<>();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    while ((line = reader.readLine()) != null) {
                        List<String> linewords = seprateWords(line);
                        for (String word :
                                linewords) {
                            if(isWord(word) && word.length()>3 && !stopwords.contains(word)){
                                allWords.add(word);
                                addIntoMap(oneTXT,word);
                                addIntoMap(wordOccurTimes,word);
                            }
                        }

                    }
                    allTXT.add(oneTXT);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("处理一篇文章出错");
                }
            }
        }
    }

    //计算一共有多少个文档
    private static int totalFileNum(){
        int sum=0;
        for (ArrayList<File> fs :
                dirs) {
            sum+=fs.size();
        }
        return sum;
    }

    //计算TF
    private static void computTF(ArrayList<TreeMap<String,Double>> allTXT){
        for (TreeMap<String, Double> oneTXT :
                allTXT) {
            for (String key :
                    oneTXT.keySet()) {
                double value = oneTXT.get(key)==0 ? 0 : (Math.log(oneTXT.get(key))+1);
                oneTXT.put(key,value);
            }
        }
    }

    //计算IDF
    private static void computIDF(TreeMap<String,Double> wordOccurTimes){
        for (String key :
                wordOccurTimes.keySet()) {
            wordOccurTimes.put(key,Math.log(1 + totalFileNum()/wordOccurTimes.get(key)));
        }
    }

    //辅助方法
    private static void addIntoMap(Map<String,Double> m,String word){
        if(m.containsKey(word)){
            m.put(word,m.get(word)+1.0);
        }
        else {
            m.put(word,1.0);
        }
    }

    //对一个字符串进行分词
    private static List<String> seprateWords(String line){
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

    //判断一个单词是否是英文单词
    private static boolean isWord(String word){
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    //计算最终的TF-IDF
    private static void computeTFIDF(){
        readStopWords();
        readAllFiles();
        processEachFile();
        computTF(allTXT);
        computIDF(wordOccurTimes);
        for (TreeMap<String, Double> m :
                allTXT) {
            TreeMap<String,Double> res = new TreeMap<>(wordOccurTimes);
            for (String word:
                 res.keySet()) {
                if(m.keySet().contains(word)){
                    res.put(word,res.get(word) * m.get(word));
                }else {
                    res.put(word,0.0);
                }
            }
            TF_IDF.add(res);
        }
    }

    //把结果写入文件
    private static void output(){
        computeTFIDF();
        BufferedWriter bw=null;
        try {
            File result = new File("./result/res.txt");
            bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(result),"utf-8"));
            for (TreeMap<String, Double> map :
                    TF_IDF) {
                for (Map.Entry<String, Double> e :
                        map.entrySet()) {
                    if(Math.abs(e.getValue())>1e-5){
                        bw.write(e.getKey()+" : "+e.getValue()+"    ");
                    }
                }
                bw.write("\n");
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        output();
    }


}
