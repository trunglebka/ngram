import java.io.*;
import java.util.ArrayList;
import java.util.List;
import opennlp.tools.ngram.NGramModel;
import gnu.trove.map.TObjectDoubleMap;
/**
 * Created by trung on 08-Apr-17.
 */
public class Ngram {
public static void main(String[] args) throws IOException {
    long begin= System.currentTimeMillis();
//    Utils.cleanText("E:\\workspace\\data\\ngram\\raw\\viwiki-clean.txt","E:\\workspace\\data\\ngram\\filtered\\viwiki-clean.txt",Utils.loadDict("tudon.txt"));
//   Utils.replaceSpecialCharToFile("input.txt","output.txt");



//    List<String> list1= Utils.hashMapToListString(Utils.calculateNgram_Probability(Utils.sortByValue(Utils.calculateNgramInDirectory("E:\\workspace\\data\\ngram\\filtered",1))));
//    Utils.writeToFile("E:\\workspace\\data\\ngram\\ngram_output\\unigram.txt",list1);

    List<String> list1= Utils.hashMapToListString(Utils.sortByValue(Utils.calculateNgram_Probability(Utils.calculateNgramInDirectory("D:\\workspace\\data\\ngram\\filtered",1))));
    Utils.writeToFile("D:\\workspace\\data\\ngram\\ngram_output\\unigram.txt",list1);
//
//
//   / Utils.writePatriciaTrieToFile("D:\\workspace\\data\\ngram\\ngram_output\\trigram\\4gramProbabilityWordTrove.txt",Utils.calculateNgram_ProbabilityWithPatriciaTrie(Utils.calculateNgramInDirectoryWithPatriciaTrie("D:\\workspace\\data\\ngram\\filtered",4)));
//
//    List<String> list2= Utils.hashMapToListString(Utils.calculateNgram_Probability(Utils.sortByValue(Utils.calculateNgramInDirectory("D:\\workspace\\data\\ngram\\filtered",2))));
//    Utils.writeToFile("D:\\workspace\\data\\ngram\\ngram_output\\bigram.txt",list2);





    System.out.println("Thoi gian chay: "+ (System.currentTimeMillis()-begin)/1000+"s");
}
}
