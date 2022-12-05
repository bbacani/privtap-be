package hr.fer.dsd.privtap.model.user;

import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivacyPreference {
    private String platform;
    private Map<RequestFieldName, List<String>> preferences;
}
