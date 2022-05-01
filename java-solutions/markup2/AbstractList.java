package markup2;

import java.util.List;

public abstract class AbstractList extends ListOrParagraph {
    protected AbstractList(List<ListItem> content, String htmlTag) {
        super(content, "?", htmlTag);
    }
}
