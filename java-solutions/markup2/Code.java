package markup2;

import java.util.List;

public class Code extends EffectText {
    public Code(List<ParagraphInner> content) {
        super(content, "`", "code");
    }
}
