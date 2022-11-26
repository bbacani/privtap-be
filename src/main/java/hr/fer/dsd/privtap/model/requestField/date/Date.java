package hr.fer.dsd.privtap.model.requestField.date;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Date extends RequestField implements Serializable {

    public Date(String value) {
        this.setValue(value);
    }
    public Date buildDefault(RequestFieldName name){
        var date=new Date();
        date.setName(name);
        date.setValue(null);
        return date;
    }
}
