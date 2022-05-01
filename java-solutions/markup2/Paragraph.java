package markup2;

import java.util.List;

public class Paragraph extends ListOrParagraph {
    public Paragraph(List<ParagraphInner> content) {
        super(content, "", "p");
    }
}
