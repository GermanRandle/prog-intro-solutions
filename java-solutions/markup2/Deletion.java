package markup2;

import java.util.List;

public class Deletion extends EffectText {
    public Deletion(List<ParagraphInner> content) {
        super(content, "}}", "del");
    }
}
