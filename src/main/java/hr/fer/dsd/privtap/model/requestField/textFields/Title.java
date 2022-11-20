package hr.fer.dsd.privtap.model.requestField.textFields;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Title extends RequestField {
    public Title(String content) {
        this.setValue(content);
    }
    public Title buildDefault(RequestFieldName name){
        var title=new Title();
        title.setName(name);
        title.setValue(null);
        return title;
    }
}
