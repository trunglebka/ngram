import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by trungle on 31/03/2017.
 */
public class runner {
    public static void main(String [] args) throws IOException {
        List<String> dictList= Utils.readLinesFromFile("D:\\workspace\\data\\dict\\ngulieu\\dico.txt");
        List<String> dicts= new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        for ( String word: dictList) {
            String []arr= word.split("\t+");
            dicts.add(arr[0]);

        }
       Utils.writeToFile("C:\\users\\trung\\desktop\\tudien.txt",dicts);
//        HashMap<String,String> sortedMap= Utils.sortByKey(map);
//        List<String> sortedDict= Utils.hashMapToListWord(sortedMap);
//        for(String word: sortedDict){
//            System.out.println(word);
//        }


    }
}
