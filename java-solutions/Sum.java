public class Sum {
    public static void main(String[] args) {
        int answer = 0;
        for (int i = 0; i < args.length; i++) {
            int numBegin = 0;
            for (int j = 0; j < args[i].length(); j++) {
                if (Character.isWhitespace(args[i].charAt(j))) {
                    if (numBegin < j) {
                        answer += Integer.parseInt(args[i].substring(numBegin, j));
                    }
                    numBegin = j + 1;
                }
            }
            if (numBegin < args[i].length()) {
                answer += Integer.parseInt(args[i].substring(numBegin));
            }
        }
        System.out.println(answer);
    }
}
