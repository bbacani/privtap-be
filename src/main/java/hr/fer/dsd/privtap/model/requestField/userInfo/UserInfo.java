package hr.fer.dsd.privtap.model.requestField.userInfo;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserInfo extends RequestField implements Serializable {

    private class Info {
        private String username;
        private String firstName;
        private String lastName;
        private String email;

        public Info(String username, String firstName, String lastName, String email) {
            this.username = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }
    }
    public UserInfo buildDefault(RequestFieldType type, String maskedValue) {
        this.setType(type);
        this.setMaskedValue(maskedValue);
        return this;
    }
}
