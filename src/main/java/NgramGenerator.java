/**
 * Created by trung on 12-Apr-17.
 */
import java.util.*;

public class NgramGenerator {

    public static List<String> generate(String[] input,int n, String seperator) {
        List<String> ngrams = new ArrayList<String>();

        for (int i = 0; i < input.length - n + 2; i++)
            if(i + n <= input.length)
                ngrams.add(concat(input, i, i+n, seperator));
        return ngrams;
    }

    public static String concat(String[] words, int start, int end, String seperator) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? seperator : "") + words[i]);
        return sb.toString();
    }

}