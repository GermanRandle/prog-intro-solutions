package markup2;

import java.util.List;

public class Emphasis extends EffectText {
    public Emphasis(List<ParagraphInner> content) {
        super(content, "*", "em");
    }
}
