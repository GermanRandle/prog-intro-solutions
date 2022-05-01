import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;
import java.io.IOException;

public class ReverseSum2 {
    public static void main(String[] args) {
        Predicate<Character> isSpaceSymbol = x -> x == ' ';
        ArrayList<MyList> input = new ArrayList<>();
        int longestRow = 0;
        
        try {
            MyScanner in = new MyScanner(System.in, isSpaceSymbol);
            MyList cur = new MyList();
            while (true) {
                if (in.lineHasNext()) {
                    cur.add(in.nextInt());
                } else if (in.isEnded()) {
                    break;
                } else {
                    input.add(cur);
                    longestRow = Integer.max(longestRow, cur.size());
                    cur = new MyList();
                    in.skipLine();
                }
            }
            input.trimToSize();
        } catch (IOException e) {
            System.err.printf("Error occurred while reading: %s", e.getMessage());
        }
        
        
        int[] colSum = new int[longestRow];
        int prevSum = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                colSum[j] += input.get(i).get(j);
                int curSum = prevSum + colSum[j];
                System.out.printf("%s ", curSum);
                prevSum = curSum;
            }
            System.out.println();
            prevSum = 0;
        }
    }
}
