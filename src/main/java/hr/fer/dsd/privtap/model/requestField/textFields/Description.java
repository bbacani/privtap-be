package hr.fer.dsd.privtap.model.requestField.textFields;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Description extends RequestField {
    public Description(String content) {
        this.setValue(content);
    }

    public Description buildDefault(RequestFieldType type, String maskedValue) {
        this.setType(type);
        this.setMaskedValue(maskedValue);
        return this;
    }
}
