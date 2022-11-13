package hr.fer.dsd.privtap.model.requestField.image;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Image extends RequestField {

    public Image(String url) {
        this.setName("image");
        this.setValue(url);
    }
}
