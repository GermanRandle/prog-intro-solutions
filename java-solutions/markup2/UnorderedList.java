package markup2;

import java.util.List;

public class UnorderedList extends AbstractList {
    public UnorderedList(List<ListItem> content) {
        super(content, "ul");
    }
}
