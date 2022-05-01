package markup;

import java.util.List;

public class ListItem extends MultiText {
    public ListItem(List<ListOrParagraph> content) {
        super(content, "?", "li");
    }
}
