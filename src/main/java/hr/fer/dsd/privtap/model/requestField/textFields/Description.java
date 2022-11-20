package hr.fer.dsd.privtap.model.requestField.textFields;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Description extends RequestField {
    public Description(String content) {
        this.setValue(content);
    }

    public Description buildDefault(RequestFieldName name){
        var description=new Description();
        description.setName(name);
        description.setValue(null);
        return description;
    }
}
