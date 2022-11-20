package hr.fer.dsd.privtap.model.requestField.userInfo;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo extends RequestField{

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
}
