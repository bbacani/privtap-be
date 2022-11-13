package hr.fer.dsd.privtap.model.requestField.textFields;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Content extends RequestField {
    public Content(String content) {
        this.setName("content");
        this.setValue(content);
    }
}
