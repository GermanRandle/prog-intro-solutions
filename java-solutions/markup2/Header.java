package markup2;

import java.util.List;

public class Header extends MultiText {
    public Header(List<ParagraphInner> content, int level) {
        super(content, "#", "h" + level);
    }
}
