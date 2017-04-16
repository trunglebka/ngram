import gnu.trove.iterator.TObjectDoubleIterator;
import gnu.trove.map.hash.TObjectDoubleHashMap;

import java.io.*;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.*;
import opennlp.tools.ngram.NGramGenerator;
import org.apache.commons.collections4.trie.PatriciaTrie;
public class Utils {


    //Clean Text
    public static void cleanText(String inurl, String outurl, List<String> dict) throws IOException {
        System.out.println("Loading file..........");
        File file = new File(inurl);
        BufferedReader br = new BufferedReader(new FileReader(file));
        File outFile = new File(outurl);
        BufferedWriter wr = new BufferedWriter(new FileWriter(outFile));
        String st;
        System.out.println("Done...");
        System.out.println("Start replace...");
        while ((st = br.readLine()) != null) {
            st = st.replaceAll("[-+.^:,|?!@#$%^=/&*()\"\'<>;”“]", " ").replaceAll("[0-9]", " ").toLowerCase();

            String[] tokens = st.split("[ + \\t+.+]");
            ArrayList<String> filteredTokens = new ArrayList<>();
            for (String token : tokens) {
                if (dict.contains(token))
                    filteredTokens.add(token);
            }
            for (String str : filteredTokens) {
                wr.write(str + " ");
            }
            wr.write("\n");

        }
        System.out.println("Replace special char..... Done!");
        wr.close();
        br.close();

    }

    public static void replaceSpecialCharToFile(String inurl, String outurl) throws IOException {
        System.out.println("Loading file..........");
        File file = new File(inurl);
        BufferedReader br = new BufferedReader(new FileReader(file));
        File outFile= new File(outurl);
        BufferedWriter wr= new BufferedWriter(new FileWriter(outFile));
        String st;
        System.out.println("Done...");
        System.out.println("Start replace...");
        while((st=br.readLine()) != null){
            st =st.replaceAll("[-+.^:,|?!@#$%^=/&*()\"\'<>;”“]"," ").replaceAll("[0-9]"," ").toLowerCase();
            wr.write(st+"\n");


        }
        System.out.println("Replace special char..... Done!");
        wr.close();

    }

    public static void replaceSpecialChar(List<String> list, String ouputurl) throws IOException {

        File outFile= new File(ouputurl);
        BufferedWriter wr= new BufferedWriter(new FileWriter(outFile));
        for(String str: list){
            wr.write(str+"\n");
        }
    }

    public static ArrayList<String> replaceSpecialChar(String url) throws IOException {
        File file = new File(url);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> result= new ArrayList<>();
        String st;
        while((st=br.readLine()) != null){
            st =st.replaceAll("[-+.^:,|?!@#$%^=/&*()\"\'<>;”“]"," ").replaceAll("[0-9]"," ");
            result.add(st);
        }
        return result;
    }

    public static void writeToFile(String url, List<String> list) throws IOException {
        File outFile= new File(url);
        BufferedWriter wr= new BufferedWriter(new FileWriter(outFile));
        for(String str:list){
            wr.write(str+"\n");
        }
        wr.close();
    }


    //Text utilities
    public static void writePatriciaTrieToFile(String url, PatriciaTrie<Double> map) throws IOException {
        File outFile= new File(url);
        BufferedWriter wr= new BufferedWriter(new FileWriter(outFile));
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(10);
        for(Iterator<Map.Entry<String,Double>> it = map.entrySet().iterator(); it.hasNext();){
            Map.Entry<String, Double> entry = it.next();

            wr.write(entry.getKey()+"\t"+ nf.format(entry.getValue())+"\n");
        }
        wr.close();
    }

    public static List<String> getTokenFromFile(String url, int stopline) throws IOException {
        List<String> listString= new ArrayList<>();
        File file = new File(url);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int i=0;
        while((st=br.readLine()) != null){
            if(i< stopline) {
                i++;
                String[] temp = st.split(" ");
                for (String s : temp)
                    if (!"\t ".contains(s))
                        listString.add(s);
            }
            else break;
        }
        return listString;
    }

    public static List<String> getAllTokenFromFile(String url) throws IOException {
        List<String> listString= new ArrayList<>();
        File file = new File(url);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int i=0;
        while((st=br.readLine()) != null){
                i++;
                String[] temp = st.split(" ");
                for (String s : temp)
                    if (!"\t ".contains(s))
                        listString.add(s);
        }
        return listString;
    }

    public static List<String> readLinesFromFile(String url) throws IOException {
        List<String> listString= new ArrayList<>();
        File file = new File(url);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while((st=br.readLine()) != null){
            listString.add(st);
        }
        br.close();
        return listString;
    }

    public static List<String> getTokenFromStringList(List<String> list){
        List<String> listString= new ArrayList<>();
        for(String str: list){
            String[] temp= str.split(" ");
            for(String s:temp)
                if(!"\t ".contains(s))
                    listString.add(s);
        }
        return listString;
    }



    //Ngram
    public static List<String> calulateNgram(String[] input,int n, String separator){
        System.out.println("Calculating ngrams.......");
        List<String> ngrams= NgramGenerator.generate(input, n, separator);
        System.out.println("Calculating ngrams........Done");
        return ngrams;
    }

    public static HashMap<String,Double>  calculate_ngram_count(List<String> ngram){
        HashMap<String,Double> map = new HashMap<String,Double>();
        for(String str: ngram){
            Double val = map.get(str);
            if(val != null){
                map.put(str, new Double(val + 1));
            }else{
                map.put(str,(double)1);
            }
        }
        for(Iterator<Map.Entry<String,Double>> it = map.entrySet().iterator(); it.hasNext();){
            Map.Entry<String, Double> entry = it.next();
            if (entry.getValue() < 10) {
                it.remove();
            }
        }
        return map;

    }

    public static HashMap<String,Double> calculateNgram_Probability(HashMap<String, Double> ngram_count){
        HashMap<String,Double> map= new HashMap<>();
        double totalCount=0.000000;
        for(Iterator<Map.Entry<String,Double>> it = ngram_count.entrySet().iterator(); it.hasNext();){
            Map.Entry<String, Double> entry = it.next();
            totalCount+=entry.getValue();
        }
        for(Iterator<Map.Entry<String,Double>> it = ngram_count.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Double> entry = it.next();
            map.put(entry.getKey(),Math.round(entry.getValue())/totalCount);
        }
        return map;
    }

    public static PatriciaTrie<Double> calculateNgram_ProbabilityWithPatriciaTrie(PatriciaTrie<Double> ngram_count){
        PatriciaTrie<Double> map= new PatriciaTrie<>();
        double totalCount=0.000000;
        for(Iterator<Map.Entry<String,Double>> it = ngram_count.entrySet().iterator(); it.hasNext();){
            Map.Entry<String, Double> entry = it.next();
            totalCount+=entry.getValue();
        }
        for(Iterator<Map.Entry<String,Double>> it = ngram_count.entrySet().iterator(); it.hasNext();){
            Map.Entry<String, Double> entry = it.next();
            map.put(entry.getKey(),Math.round(entry.getValue())/totalCount);
        }
        return map;
    }

    //Ngram with directory
    public static HashMap<String,Double> calculateNgramInDirectory(String dirUrl, int n) throws IOException {
        HashMap<String,Double> map = new HashMap<>();
        File dir = new File(dirUrl);
        if(dir.isDirectory()){
            List<File> files= Arrays.asList(dir.listFiles());
            for (File file:files){
                if (file.isDirectory()) continue;
                BufferedReader br= new BufferedReader(new FileReader(file));
                String st;
                while(!((st = br.readLine()) == null)){
                    List<String> ngram= NgramGenerator.generate(st.split(" "),n," ");
                    for(String str: ngram){
                        Double val = map.get(str);
                        if(val != null){
                            map.put(str, new Double(val + 1));
                        }else{
                            map.put(str,new Double(1));
                        }
                    }
                }

            }
        }
        else System.out.println("Can nhap vao thu muc");

        return map;
    }

    public static PatriciaTrie<Double> calculateNgramInDirectoryWithPatriciaTrie(String dirUrl, int n) throws IOException {
        PatriciaTrie<Double> map = new PatriciaTrie<>();
        File dir = new File(dirUrl);
        if(dir.isDirectory()){
            File []files= dir.listFiles();
            for (File file:files){
                if (file.isDirectory()) continue;
                BufferedReader br= new BufferedReader(new FileReader(file));
                String st;
                while(!((st = br.readLine()) == null)){
                    List<String> ngram= NgramGenerator.generate(st.split(" "),n," ");
                    for(String str: ngram){
                        Double val = map.get(str);
                        if(val != null){
                            map.put(str, val + 1);
                        }else{
                            map.put(str,new Double(1));
                        }
                    }
                }

            }
        }
        else System.out.println("Can nhap vao thu muc");

        return map;
    }


    //Map to List<String>

    public static List<String> hashMapToListString(HashMap<String, Double> map){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(10);
        ArrayList<String> list= new ArrayList<>();
        for(Map.Entry<String, Double> entry: map.entrySet()) {
            String str = entry.getKey()+"\t"+ nf.format(entry.getValue());
            list.add(str);
        }
        return list;
    }

    public static List<String> hashMapToListXMLString(HashMap<String, Double> ngram_count){
        double maxtotalCount=0.000000;
        Map.Entry<String, Double> maxEntry = null;

        for (Map.Entry<String, Double> entry : ngram_count.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        maxtotalCount=maxEntry.getValue();
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        ArrayList<String> list= new ArrayList<>();
        for(Map.Entry<String, Double> entry: ngram_count.entrySet()) {
            //String str= " <w f=\""+ nf.format(255*Math.log10(entry.getValue())/Math.log10(maxtotalCount))+"\" flags=\"\">"+entry.getKey()+"</w>";
            String str= nf.format(255*Math.log10(entry.getValue())/Math.log10(maxtotalCount))+"\t"+entry.getKey();
            list.add(str);
        }
        return list;
    }


    public static List<String> patriciaTrieToListString(PatriciaTrie<Double> map){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(10);
        ArrayList<String> list= new ArrayList<>();
        for(Iterator<Map.Entry<String,Double>> it = map.entrySet().iterator(); it.hasNext();){
            Map.Entry<String, Double> entry = it.next();
            String str = entry.getKey()+"\t"+ nf.format(entry.getValue());
            list.add(str);
        }
        return list;
    }


    public static List<String> hashMapToListWord(HashMap<String, String> map){
        ArrayList<String> list= new ArrayList<>();
        for(Map.Entry<String, String> entry: map.entrySet()) {
            String str = entry.getKey();
            list.add(str);
        }
        return list;
    }

    public static List<String> loadDict(String url) throws IOException {
        List<String> listString= new ArrayList<>();
        File file = new File(url);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while(!((st = br.readLine()) == null)){
            listString.add(st);
        }
        return listString;
    }

    public static HashMap<String,Double> filterHashMapFromDict(HashMap<String,Double> map, List<String> dict){
        for(Iterator<Map.Entry<String,Double>> it = map.entrySet().iterator(); it.hasNext();){
            Map.Entry<String, Double> entry = it.next();
            if (dict.contains(entry.getKey())) {
                it.remove();
            }
        }
        return map;
    }

    public static <K, V extends Comparable<? super V>> HashMap<K, V> sortByValue( Map<K, V> map ) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return ( o1.getValue() ).compareTo( o2.getValue() );
            }
        } );

        HashMap<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
/*
    public static <K extends String, V extends Comparable<? super V>> PatriciaTrie<V> sortPatriciaTrieByValue( PatriciaTrie<V> map ) {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return ( o1.getValue() ).compareTo( o2.getValue() );
            }
        } );

        PatriciaTrie<V> result = new PatriciaTrie<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
*/
    public static <K extends Comparable<? super K>, V > HashMap<K, V> sortByKey( Map<K, V> map ) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return ( o1.getKey() ).compareTo( o2.getKey() );
            }
        } );

        HashMap<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    /*
    public static <K extends String, V > PatriciaTrie<V> sortPatriciaTrieByKey( PatriciaTrie<Double> map ) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return ( o1.getKey() ).compareTo( o2.getKey() );
            }
        } );

        PatriciaTrie<V> result = new PatriciaTrie<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
    */


}