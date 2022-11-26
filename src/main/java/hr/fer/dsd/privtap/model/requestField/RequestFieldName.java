package hr.fer.dsd.privtap.model.requestField;

import hr.fer.dsd.privtap.model.requestField.date.Date;
import hr.fer.dsd.privtap.model.requestField.image.Image;
import hr.fer.dsd.privtap.model.requestField.location.Location;
import hr.fer.dsd.privtap.model.requestField.textFields.Content;
import hr.fer.dsd.privtap.model.requestField.textFields.Description;
import hr.fer.dsd.privtap.model.requestField.textFields.Title;
import hr.fer.dsd.privtap.model.requestField.userInfo.UserInfo;

import java.io.Serializable;

public enum RequestFieldName implements Serializable {

    date, image, location, content, description, title, userinfo;

    public Object getRelatedClass() {
        switch (this) {
            case date:
                return new Date();
            case content:
                return new Content();
            case image:
                return new Image();
            case description:
                return new Description();
            case location:
                return new Location();
            case title:
                return new Title();
            case userinfo:
                return new UserInfo();
            default: return RequestField.class;
        }
    }
}
