import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class WordStatInput {
    public static boolean isWordSymbol(char c) {
        return Character.getType(c) == Character.DASH_PUNCTUATION || Character.isLetter(c) || c == '\'';
    }
    
    public static String[] extendArray(String[] arr) {
        String[] ext = new String[arr.length * 2];
        for (int i = 0; i < arr.length; i++) {
            ext[i] = arr[i];
        }
        return ext;
    }
    
    public static int findFirst(String[] arr, String s) {
        int l = -1, r = arr.length - 1, mid;
        while (r - l > 1) {
            mid = (l + r) / 2;
            if (arr[mid].compareTo(s) < 0) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return r;
    }
    
    public static int findLast(String[] arr, String s) {
        int l = 0, r = arr.length, mid;
        while (r - l > 1) {
            mid = (l + r) / 2;
            if (arr[mid].compareTo(s) <= 0) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return l;
    }
    
    public static void main(String[] args) {
        final int INIT_LENGTH = 1;
    	String[] order = new String[INIT_LENGTH];
    	int wordsCount = 0;
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(args[0]),
                    "utf8"
                )
            );
            try {
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    int wordBegin = 0;
                    for (int i = 0; i <= line.length(); i++) {
                        if (i == line.length() || !isWordSymbol(line.charAt(i))) {
                            if (wordBegin != i) {
                            	if (wordsCount == order.length) {
                            	    order = extendArray(order);
                            	}
                                order[wordsCount] = line.substring(wordBegin, i).toLowerCase();
                                wordsCount++;
                            }
                            wordBegin = i + 1;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.printf("I/O error occured while reading: %s", e.getMessage());
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file is not found");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported encoding in input file");
        } catch (IOException e) {
            System.out.printf("I/O error occured while reading: %s", e.getMessage());
        }
        
        String[] words = new String[wordsCount];
        for (int i = 0; i < wordsCount; i++) {
            words[i] = order[i];
        }
        Arrays.sort(words);
        boolean[] used = new boolean[wordsCount];
       
        try {
            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    "utf8"
                )                
            );
            try {
                for (int i = 0; i < wordsCount; i++) {
                    int leftId = findFirst(words, order[i]);
                    int rightId = findLast(words, order[i]);
                    if (!used[leftId]) {
                        used[leftId] = true;
                        writer.write(order[i] + " " + (rightId - leftId + 1));
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                System.out.printf("I/O error occured while writing: %s", e.getMessage());
            } finally {
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Output file is not found");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported encoding in output file");
        } catch (IOException e) {
            System.out.printf("I/O error occured while writing: %s", e.getMessage());
        }
    }
}
