package hr.fer.dsd.privtap.model.requestField.image;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
public class Image extends RequestField {

    public Image(String url) {
        this.setName("image");
        this.setValue(url);
    }
}
