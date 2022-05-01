package markup;

import java.util.List;

public class Paragraph extends ListOrParagraph {
    public Paragraph(List<ParagraphInner> content) {
        super(content, "", "?");
    }

    @Override
    public void toHtml(StringBuilder sb) {
        for (MultiText part : content) {
            part.toHtml(sb);
        }
    }
}
