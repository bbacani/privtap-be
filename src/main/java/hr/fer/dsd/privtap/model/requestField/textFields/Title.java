package hr.fer.dsd.privtap.model.requestField.textFields;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Title extends RequestField {
    public Title(String content) {
        this.setName("title");
        this.setValue(content);
    }
}
