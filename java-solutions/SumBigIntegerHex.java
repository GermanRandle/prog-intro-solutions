import java.math.BigInteger;

public class SumBigIntegerHex {
    public static BigInteger stringToNum(String s) {
        if (s.length() >= 2 && s.charAt(0) == '0' && Character.toLowerCase(s.charAt(1)) == 'x') {
            return new BigInteger(s.substring(2), 16);
        } else {
            return new BigInteger(s, 10);
        }
    }
    
    public static void main(String[] args) {
        BigInteger answer = BigInteger.ZERO;
        for (int i = 0; i < args.length; i++) {
            int numBegin = 0;
            for (int j = 0; j <= args[i].length(); j++) {
                if (j == args[i].length() || Character.isWhitespace(args[i].charAt(j))) {
                    if (numBegin != j) {
                        answer = answer.add(stringToNum(args[i].substring(numBegin, j)));
                    }
                    numBegin = j + 1;
                }
            }
        }
        System.out.println(answer);
    }
}
