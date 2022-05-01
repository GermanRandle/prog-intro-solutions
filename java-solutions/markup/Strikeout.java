package markup;

import java.util.List;

public class Strikeout extends EffectText {
    public Strikeout(List<ParagraphInner> content) {
        super(content, "~", "s");
    }
}
