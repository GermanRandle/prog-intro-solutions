package markup;

import java.util.List;

public class Text extends ParagraphInner implements MarkdownAndHtml {
    private final String content;

    public Text(String content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(content);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append(content);
    }
}