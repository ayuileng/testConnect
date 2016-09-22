package main;
import org.wltea.analyzer.core.IKSegmenter;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.wltea.analyzer.core.Lexeme;


public class Test1 {
    @Test
    /**
     * 加载停止词
     */
    public void test1() throws Exception{
        Set<String> stopwords = new HashSet<>();
        System.out.println("加载停止词...");
        String line;
        String dict_filename = "./stopword/stop.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(dict_filename),"utf-8"));
        while((line = br.readLine())!=null){
            stopwords.add(line);
        }
        System.out.println(stopwords.size());
        System.out.println("停止词加载完毕");
    }
    @Test
    public void test2() throws Exception {
        List<List<String>> articles = new ArrayList<>();
        File filepath = new File("./files/");
        List<File[]> dirs = new ArrayList<>();
        for (int i=0;i<filepath.listFiles().length;i++){
            File[] files = filepath.listFiles()[i].listFiles();
            dirs.add(files);
        }
        for (File[] txts :
                dirs) {
            for (File f :
                    txts) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
                String line;
                while((line=reader.readLine())!=null){

                }
            }
        }
    }
    @Test
    public void test3() throws IOException {
        String line = "Computer ,Science我和你说，你为什么不说话 Department, 123 Carnegie Mellon University";
        IKSegmenter segmenter =  new IKSegmenter(new StringReader(line),true);
        Lexeme word;
        String word_text;
        while((word = segmenter.next())!=null){
            System.out.println(word.getLexemeText());
        }
    }

}
