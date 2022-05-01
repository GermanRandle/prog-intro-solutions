package markup2;

import java.util.List;

public class OrderedList extends AbstractList {
    public OrderedList(List<ListItem> content) {
        super(content, "ol");
    }
}
