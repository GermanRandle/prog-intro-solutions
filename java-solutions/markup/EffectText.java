package markup;

import java.util.List;

public abstract class EffectText extends ParagraphInner {
    protected EffectText(List<ParagraphInner> content, String wrapper, String htmlTag) {
        this.content = content;
        this.wrapper = wrapper;
        this.htmlTag = htmlTag;
    }
}