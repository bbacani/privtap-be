package hr.fer.dsd.privtap.model.requestField.textFields;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Description extends RequestField {
    public Description(String content) {
        this.setName("description");
        this.setValue(content);
    }
}
