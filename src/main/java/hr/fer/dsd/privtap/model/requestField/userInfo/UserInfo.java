package hr.fer.dsd.privtap.model.requestField.userInfo;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo extends RequestField{
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public UserInfo(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        this.setName("userInfo");
        this.setValue(this);
    }
}
