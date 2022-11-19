package hr.fer.dsd.privtap.model.requestField.textFields;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Description extends RequestField {
    public Description(String content) {
        this.setName("description");
        this.setValue(content);
    }
}
