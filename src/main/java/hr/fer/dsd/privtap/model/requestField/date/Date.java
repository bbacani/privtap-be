package hr.fer.dsd.privtap.model.requestField.date;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Date extends RequestField {

    public Date(String value) {
        this.setName("date");
        this.setValue(value);
    }
}
