package hr.fer.dsd.privtap.model.requestField.image;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Image extends RequestField {

    public Image(String url) {
        this.setValue(url);
    }
    public Image buildDefault(RequestFieldName name){
        this.setName(name);
        this.setValue(null);
        return this;
    }
}
