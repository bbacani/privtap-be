package hr.fer.dsd.privtap.model.requestField.textFields;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Title extends RequestField {
    public Title(String content) {
        this.setValue(content);
    }
}
