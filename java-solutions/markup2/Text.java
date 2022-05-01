package markup2;

import java.util.HashSet;
import java.util.Set;

public class Text extends ParagraphInner {
    private final String content;
    private final Set<Character> screenable = Set.of('_', '*', '{', '}', '<', '>');

    public Text(String content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(content);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        for (int i = 0; i < content.length(); i++) {
            switch (content.charAt(i)) {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '\\':
                    if (i == content.length() - 1 || !screenable.contains(content.charAt(i + 1))) {
                        sb.append('\\');
                    }
                    break;
                default:
                    sb.append(content.charAt(i));
            }
        }
    }
}