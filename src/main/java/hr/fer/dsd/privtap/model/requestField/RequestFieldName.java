package hr.fer.dsd.privtap.model.requestField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestFieldName {
    RequestFieldType type;
    String maskedValue;
}
