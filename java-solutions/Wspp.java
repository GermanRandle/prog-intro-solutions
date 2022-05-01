import java.io.File;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.function.Predicate;
import java.util.Map;

public class Wspp {
    public static void main(String[] args) {
        Predicate<Character> isSpaceSymbol = x -> !Character.isLetter(x) && x != '\''
                                             && Character.getType(x) != Character.DASH_PUNCTUATION;
        Map<String, IntList> wordsStat = new LinkedHashMap<>();
        try {
            MyScanner inFile = new MyScanner(new File(args[0]), isSpaceSymbol);
            try {
                int curId = 1;
                while (true) {
		            while (!inFile.isEnded() && !inFile.lineHasNext()) {
	                    inFile.skipLine();
	                }
	                if (inFile.isEnded()) {
	                    break;
	                }
                    String word = inFile.next();
                    word = word.toLowerCase();
                    if (wordsStat.get(word) == null) {
                        wordsStat.put(word, new IntList());
                    }
                    wordsStat.get(word).add(curId);
                    curId++;
                }
            } catch (IOException e) {
                System.err.printf("Error while reading output file: %s", e.getMessage());
            } finally {
                inFile.close();
            }
        } catch (IOException e) {
            System.err.printf("Error while trying to open or close input file: %s", e.getMessage());
        }

        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            try {
                for (Map.Entry<String, IntList> entry : wordsStat.entrySet()) {
                    writer.write(entry.getKey());
                    writer.write(" " + entry.getValue().size());
                    writer.write(" " + entry.getValue());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.printf("Error occurred while writing: %s", e.getMessage());
            } finally {
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Output file is not found");
        } catch (IOException e) {
            System.err.printf("Error occurred while opening or closing output file: %s", e.getMessage());
        }
    }
}
