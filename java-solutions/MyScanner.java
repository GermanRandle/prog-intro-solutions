import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

public class MyScanner implements AutoCloseable {
    private final int MAX_BUFFER_SIZE = 1024;
    private int curPos = 0;
    private int bufferSize = 0;
    private char[] buffer = new char[MAX_BUFFER_SIZE];
    private char[] lineSeparators = new char[]{'\n', '\r', '\u0085', '\u2028', '\u2029'};
    private Reader reader;
    private Predicate<Character> isSpaceSymbol;
    private char prevSymbol;
    
    public MyScanner(File f, Predicate<Character> p) throws IOException {
        reader = new FileReader(f, StandardCharsets.UTF_8);
        isSpaceSymbol = p;
        shiftBuffer();
    }
    
    public MyScanner(InputStream in, Predicate<Character> p) throws IOException {
        reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        isSpaceSymbol = p;
        shiftBuffer();
    }
    
    private boolean isEndOfLine(char c) {
        if (System.lineSeparator().equals("\r\n")) {
            if (c == '\r') {
                return false;
            } else if (c == '\n') {
                return prevSymbol == '\r';
            }
        } else if (System.lineSeparator().equals("\n\n")) {
            if (c == '\n') {
                return prevSymbol == '\n';
            }
        }
        for (char sep : lineSeparators) {
            if (c == sep) {
                return true;
            }
        }
        return false;
    }
    
    private void shiftBuffer() throws IOException {
        prevSymbol = buffer[curPos];
        curPos++;
    	if (curPos >= bufferSize) {
            curPos = 0;
            bufferSize = reader.read(buffer);
        }
    }
    
    private Integer fromAbcFormat(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i))) {
                sb.append((char)('0' + (s.charAt(i) - 'a')));
            } else {
                sb.append(s.charAt(i));
            }
        }
        return Integer.parseInt(new String(sb));
    }
    
    public boolean isEnded() {
        return bufferSize == -1;
    }
    
    public boolean lineHasNext() throws IOException {
        while (true) {
            if (isEndOfLine(buffer[curPos]) || isEnded()) {
                return false;
            } else if (!isSpaceSymbol.test(buffer[curPos])) {
                if (buffer[curPos] != '\r' && buffer[curPos] != '\n') {
                    return true;
                }
            }
            shiftBuffer();
        }
    }
    
    public String next() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (bufferSize == -1) {
                break;
            }
            if (isSpaceSymbol.test(buffer[curPos]) || isEndOfLine(buffer[curPos])) {
                if (sb.length() > 0) {
                    break;
                }
            } else {
                sb.append(buffer[curPos]);
            }
            shiftBuffer();
        }
        if (sb.charAt(sb.length() - 1) == '\n' || sb.charAt(sb.length() - 1) == '\r') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    
    public void skipLine() throws IOException {
        while (!isEndOfLine(buffer[curPos])) {
            shiftBuffer();
        }
        shiftBuffer();
    }
    
    public Integer nextInt() throws IOException {
        String token = next();
    	if (token.length() >= 2 && token.substring(0, 2).toLowerCase().equals("0x")) {
            return Integer.parseUnsignedInt(token.substring(2), 16);
    	}
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            return fromAbcFormat(token);
        }
    }
    
    public void close() throws IOException {
        reader.close();
    }
}
