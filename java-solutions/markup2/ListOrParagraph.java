package markup2;

import java.util.List;

public abstract class ListOrParagraph extends MultiText {
    // just a union of classes AbstractList and Paragraph

    protected ListOrParagraph(List<? extends MultiText> content, String wrapper, String htmlTag) {
        super(content, wrapper, htmlTag);
    }
}
