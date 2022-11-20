package hr.fer.dsd.privtap.model.requestField.image;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Image extends RequestField {

    public Image(String url) {
        this.setValue(url);
    }
}
