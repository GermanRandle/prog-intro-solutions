package markup2;

import java.util.List;

public abstract class MultiText implements MarkdownAndHtml {
    protected List<? extends MultiText> content;
    protected String wrapper;
    protected String htmlTag;

    protected MultiText(List<? extends MultiText> content, String wrapper, String htmlTag) {
        this.content = content;
        this.wrapper = wrapper;
        this.htmlTag = htmlTag;
    }

    protected MultiText() {}

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(wrapper);
        for (MultiText part : content) {
            part.toMarkdown(sb);
        }
        sb.append(wrapper);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append("<").append(htmlTag).append(">");
        for (MultiText part : content) {
            part.toHtml(sb);
        }
        sb.append("</").append(htmlTag).append(">");
    }
}
