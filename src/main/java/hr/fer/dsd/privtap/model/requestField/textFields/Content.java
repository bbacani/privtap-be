package hr.fer.dsd.privtap.model.requestField.textFields;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Content extends RequestField {

    public Content(String content) {
        this.setName(RequestFieldName.content);
        this.setValue(content);
    }
    public Content buildDefault(RequestFieldName name){
        this.setName(name);
        this.setValue(null);
        return this;
    }
}
