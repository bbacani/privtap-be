package hr.fer.dsd.privtap.model.user.requestField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public abstract class RequestField {

    private String name;
    private Object value;
}
