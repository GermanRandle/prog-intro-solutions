import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ReverseHexAbc2 {
    public static String toAbcFormat(int x) {
        String s = Integer.valueOf(x).toString();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                sb.append((char)('a' + (s.charAt(i) - '0')));
            } else {
                sb.append(s.charAt(i));
            }
        }
        return new String(sb);
    }
    
    public static void main(String[] args) {
    	Predicate<Character> isSpaceSymbol = x -> x == ' ';
    	MyList numbers = new MyList();
        MyList rowSizes = new MyList();
    	try {
            MyScanner in = new MyScanner(System.in, isSpaceSymbol);
            int curRowSize = 0;
            try {
                while (true) {
                    while (!in.isEnded() && !in.lineHasNext()) {
                        rowSizes.add(curRowSize);
                        curRowSize = 0;
                        in.skipLine();
                    }
                    if (in.isEnded()) {
                        break;
                    }
                    Integer curNum = in.nextInt();
                    numbers.add(curNum);
                    curRowSize++;
                }
            } catch (IOException e) {
                System.err.printf("Error occurred while reading: %s", e.getMessage());
            } finally {
                in.close();
            }
    	} catch (IOException e) {
            System.err.printf("Error occured while opening or closing input stream: %s", e.getMessage());
        }
        ArrayList<int[]> data = new ArrayList<>();
        int curId = 0;
        for (int i = 0; i < rowSizes.size(); i++) {
            int[] curRow = new int[rowSizes.get(i)];
            for (int j = 0; j < rowSizes.get(i); j++) {
                curRow[j] = numbers.get(curId);
                curId++;
            }
            data.add(curRow);
        }
        for (int i = data.size() - 1; i >= 0; i--) {
            for (int j = data.get(i).length - 1; j >= 0; j--) {
                System.out.printf("%s ", toAbcFormat(data.get(i)[j]));
            }
            System.out.println();
        }
    }
}
