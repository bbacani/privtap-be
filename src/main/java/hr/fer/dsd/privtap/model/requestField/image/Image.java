package hr.fer.dsd.privtap.model.requestField.image;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Image extends RequestField {

    public Image(String url) {
        this.setValue(url);
    }

    public Image buildDefault(RequestFieldType type, String maskedValue) {
        this.setType(type);
        this.setMaskedValue(maskedValue);
        return this;
    }
}
