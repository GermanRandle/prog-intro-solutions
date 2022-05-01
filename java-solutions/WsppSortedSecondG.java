import java.io.File;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.Map;
import java.util.HashMap;

public class WsppSortedSecondG {
    public static void main(String[] args) {
        Predicate<Character> isSpaceSymbol = x -> !Character.isLetter(x) && x != '\''
                && Character.getType(x) != Character.DASH_PUNCTUATION;
        Map<String, IntList> wordStat = new TreeMap<>();
        int wordId = 1;
        int rowId = 1;
        try {
            MyScanner in = new MyScanner(new File(args[0]), isSpaceSymbol);
            try {
                while (true) {
                    while (!in.isEnded() && !in.lineHasNext()) {
                        in.skipLine();
                        rowId++;
                    }
                    if (in.isEnded()) {
                        break;
                    }
                    String word = in.next().toLowerCase();
                    wordStat.putIfAbsent(word, new IntList());
                    wordStat.get(word).add(wordId);
                    wordStat.get(word).add(rowId);
                    wordId++;
                }
            } catch (IOException e) {
                System.err.printf("Error while reading output file: %s", e.getMessage());
            } finally {
                in.close();
            }
        } catch (IOException e) {
            System.err.printf("Error while trying to open or close input file: %s", e.getMessage());
        }
        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            try {
                for (Map.Entry<String, IntList> entry : wordStat.entrySet()) {
                    String word = entry.getKey();
                    writer.write(word);
                    writer.write(" ");
                    writer.write(Integer.toString(entry.getValue().size() / 2));
                    int cntInRow = 1;
                    for (int i = 2; i < entry.getValue().size(); i += 2) {
                        if (wordStat.get(word).get(i - 1) == wordStat.get(word).get(i + 1) && cntInRow % 2 == 1) {
                            writer.write(" ");
                            writer.write(Integer.toString(entry.getValue().get(i)));
                            cntInRow++;
                        } else {
                            cntInRow = 1;
                        }
                    }
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
