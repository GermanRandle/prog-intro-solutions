package markup2;

import java.util.List;

public class Insertion extends EffectText {
    public Insertion(List<ParagraphInner> content) {
        super(content, "}}", "ins");
    }
}
