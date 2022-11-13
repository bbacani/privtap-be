package hr.fer.dsd.privtap.model.requestField.textFields;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Content extends RequestField {
    public Content(String content) {
        this.setName("content");
        this.setValue(content);
    }
}
