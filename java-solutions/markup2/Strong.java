package markup2;

import java.util.List;

public class Strong extends EffectText {
    public Strong(List<ParagraphInner> content) {
        super(content, "__", "strong");
    }
}
