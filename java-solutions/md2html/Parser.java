package md2html;

import markup2.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    // This is a utility class
    private Parser() {}

    private enum Wrappers {
        EMPHASIS(1),
        EMPHASIS2(1),
        STRONG(2),
        STRONG2(2),
        STRIKEOUT(2),
        CODE(1),
        INSERTION_OPEN(2),
        INSERTION_CLOSE(2),
        DELETION_OPEN(2),
        DELETION_CLOSE(2),
        UNKNOWN(1);

        private final int length;

        Wrappers(int length) {
            this.length = length;
        }
    }

    private static boolean checkNext(int pos, String s, char symbol) {
        return pos < s.length() - 1 && s.charAt(pos + 1) == symbol;
    }

    private static boolean checkPrev(int pos, String s) {
        return pos == 0 || s.charAt(pos - 1) != '\\';
    }

    private static Wrappers getWrapperType(String s, int pos) {
        switch (s.charAt(pos)) {
            case '*':
                if (checkNext(pos, s, '*')) {
                    return Wrappers.STRONG;
                } else if (checkPrev(pos, s)) {
                    return Wrappers.EMPHASIS;
                }
                return Wrappers.UNKNOWN;
            case '_':
                if (checkNext(pos, s, '_')) {
                    return Wrappers.STRONG2;
                } else if (checkPrev(pos, s)) {
                    return Wrappers.EMPHASIS2;
                }
                return Wrappers.UNKNOWN;
            case '-':
                if (checkNext(pos, s, '-')) {
                    return Wrappers.STRIKEOUT;
                }
                return Wrappers.UNKNOWN;
            case '`':
                return Wrappers.CODE;
            case '<':
                if (checkNext(pos, s, '<') && checkPrev(pos, s)) {
                    return Wrappers.INSERTION_OPEN;
                }
                return Wrappers.UNKNOWN;
            case '>':
                if (checkNext(pos, s, '>') && checkPrev(pos, s)) {
                    return Wrappers.INSERTION_CLOSE;
                }
                return Wrappers.UNKNOWN;
            case '}':
                if (checkNext(pos, s, '}') && checkPrev(pos, s)) {
                    return Wrappers.DELETION_OPEN;
                }
                return Wrappers.UNKNOWN;
            case '{':
                if (checkNext(pos, s, '{') && checkPrev(pos, s)) {
                    return Wrappers.DELETION_CLOSE;
                }
                return Wrappers.UNKNOWN;
            default:
                return Wrappers.UNKNOWN;
        }
    }

    private static List<ParagraphInner> recursiveParse(String s, int lPos, int rPos, int[] pairs) {
        List<ParagraphInner> content = new ArrayList<>();
        int textStart = lPos;
        for (int i = lPos; i < rPos; i++) {
            if (pairs[i] != -1) {
                if (i != textStart) {
                    content.add(new Text(s.substring(textStart, i)));
                }
                Wrappers curWrapper = getWrapperType(s, i);
                switch (curWrapper) {
                    case EMPHASIS:
                    case EMPHASIS2:
                        content.add(new Emphasis(recursiveParse(s, i + Wrappers.EMPHASIS.length, pairs[i], pairs)));
                        break;
                    case STRONG:
                    case STRONG2:
                        content.add(new Strong(recursiveParse(s, i + Wrappers.STRONG.length, pairs[i], pairs)));
                        break;
                    case STRIKEOUT:
                        content.add(new Strikeout(recursiveParse(s, i + Wrappers.STRIKEOUT.length, pairs[i], pairs)));
                        break;
                    case CODE:
                        content.add(new Code(recursiveParse(s, i + Wrappers.CODE.length, pairs[i], pairs)));
                        break;
                    case INSERTION_OPEN:
                        content.add(new Insertion(recursiveParse(s, i + Wrappers.INSERTION_OPEN.length, pairs[i], pairs)));
                        break;
                    case DELETION_OPEN:
                        content.add(new Deletion(recursiveParse(s, i + Wrappers.DELETION_OPEN.length, pairs[i], pairs)));
                        break;
                    default:
                        break;
                }
                if (curWrapper != Wrappers.UNKNOWN) {
                    i = pairs[i] + curWrapper.length - 1;
                    textStart = i + 1;
                }
            }
        }
        if (textStart != rPos) {
            content.add(new Text(s.substring(textStart, rPos)));
        }
        return content;
    }

    public static List<ParagraphInner> parse(String s) {
        int[] pairs = new int[s.length()];
        int[] free = new int[Wrappers.values().length];
        Arrays.fill(free, -1);
        Arrays.fill(pairs, -1);
        for (int i = 0; i < s.length(); i++) {
            Wrappers curWrapper = getWrapperType(s, i);
            if (curWrapper == Wrappers.INSERTION_OPEN || curWrapper == Wrappers.DELETION_OPEN) {
                int ord = curWrapper.ordinal();
                free[ord] = i;
            } else if (curWrapper == Wrappers.INSERTION_CLOSE || curWrapper == Wrappers.DELETION_CLOSE) {
                int ord = curWrapper.ordinal() - 1;
                if (free[ord] != -1) {
                    pairs[free[ord]] = i;
                    free[ord] = -1;
                }
            } else if (curWrapper != Wrappers.UNKNOWN) {
                int ord = curWrapper.ordinal();
                if (free[ord] == -1) {
                    free[ord] = i;
                } else {
                    pairs[free[ord]] = i;
                    free[ord] = -1;
                }
            }
            i += curWrapper.length - 1;
        }
        return recursiveParse(s, 0, s.length(), pairs);
    }
}