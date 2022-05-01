package md2html;

import markup2.Paragraph;
import markup2.Header;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Md2Html {
    public static void main(String[] args) {
        List<String> fullText = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(args[0], UTF_8))) {
            fullText = in.lines().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file is not found " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Error occurred while reading input file " + e.getMessage());
            return;
        }

        StringBuilder currentPart = new StringBuilder();
        StringBuilder readyHtml = new StringBuilder();
        fullText.add("");
        for (String line : fullText) {
            if (line.isEmpty()) {
                if (currentPart.length() > 0) {
                    String part = currentPart.toString();
                    int level = getHeaderLevel(part);
                    if (level != 0) {
                        new Header(Parser.parse(part.substring(level + 1)), level).toHtml(readyHtml);
                    } else {
                        new Paragraph(Parser.parse(part)).toHtml(readyHtml);
                    }
                    currentPart = new StringBuilder();
                    readyHtml.append(System.lineSeparator());
                }
            } else {
                if (currentPart.length() > 0) {
                    currentPart.append(System.lineSeparator());
                }
                currentPart.append(line);
            }
        }

        try (BufferedWriter out = new BufferedWriter(new FileWriter(args[1], UTF_8))) {
            out.write(readyHtml.toString());
        } catch (IOException e) {
            System.err.println("Error occurred while writing in output file " + e.getMessage());
        }
    }

    public static int getHeaderLevel(String s) {
        // Returns 0 if string is not a header, level otherwise
        if (s.charAt(0) != '#') {
            return 0;
        }
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                return i;
            } else if (s.charAt(i) != '#') {
                return 0;
            }
        }
        return 0;
    }
}