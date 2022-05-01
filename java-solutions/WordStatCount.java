import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

class Triple implements Comparable<Triple> {
    String word;
    int cnt, index;

    public Triple(String word, int cnt, int index) {
        this.word = word;
        this.cnt = cnt;
        this.index = index;
    }
    
    @Override
    public int compareTo(Triple t) {
        if (this.cnt < t.cnt || (this.cnt == t.cnt && this.index < t.index)) {
            return -1;
        } else {
            return 1;
        }
    }
}

public class WordStatCount {
    public static int findFirst(List<String> arr, String s) {
        int l = -1, r = arr.size() - 1, mid;
        while (r - l > 1) {
            mid = (l + r) / 2;
            if (arr.get(mid).compareTo(s) < 0) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return r;
    }
    
    public static int findLast(List<String> arr, String s) {
        int l = 0, r = arr.size(), mid;
        while (r - l > 1) {
            mid = (l + r) / 2;
            if (arr.get(mid).compareTo(s) <= 0) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return l;
    }
    
    public static void main(String[] args) {
        List<String> order = new ArrayList<String>();
        Predicate<Character> isSpaceSymbol = x -> !Character.isLetter(x) && x != '\''
                                             && Character.getType(x) != Character.DASH_PUNCTUATION;
        try {
            MyScanner reader = new MyScanner(new File(args[0]), isSpaceSymbol);
            try {
                while (true) {
                    while (!reader.lineHasNext() && !reader.isEnded()) {
                        reader.skipLine();
                    }
                    if (reader.isEnded()) {
                        break;
                    }
                    String word = reader.next();
                    order.add(word.toLowerCase());
                }
            } catch (IOException e) {
                System.err.printf("Error occured while reading: %s", e.getMessage());
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.err.printf("Error occured while opening or closing input file: %s", e.getMessage());
        }
        
        List<String> words = new ArrayList<String>(order);
        Collections.sort(words);
        boolean[] used = new boolean[words.size()];
        List<Triple> triples = new ArrayList<Triple>();
        for (int i = 0; i < words.size(); i++) {
            int leftId = findFirst(words, order.get(i));
            int rightId = findLast(words, order.get(i));
            if (!used[leftId]) {
                used[leftId] = true;
                triples.add(new Triple(order.get(i), rightId - leftId + 1, triples.size()));
            }
        }
        Collections.sort(triples);
       
        try {
            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    StandardCharsets.UTF_8
                )                
            );
            try {
                for (int i = 0; i < triples.size(); i++) {
                    writer.write(triples.get(i).word + " " + triples.get(i).cnt);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.printf("Error occured while writing: %s", e.getMessage());
            } finally {
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Output file is not found"); 
        } catch (IOException e) {
            System.err.printf("Error occured while trying to close output file: %s", e.getMessage());
        }
    }
}
