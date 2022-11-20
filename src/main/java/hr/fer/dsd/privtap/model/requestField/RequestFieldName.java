package hr.fer.dsd.privtap.model.requestField;

import hr.fer.dsd.privtap.model.requestField.date.Date;
import hr.fer.dsd.privtap.model.requestField.image.Image;
import hr.fer.dsd.privtap.model.requestField.location.Location;
import hr.fer.dsd.privtap.model.requestField.textFields.Content;
import hr.fer.dsd.privtap.model.requestField.textFields.Description;
import hr.fer.dsd.privtap.model.requestField.textFields.Title;
import hr.fer.dsd.privtap.model.requestField.userInfo.UserInfo;

public enum RequestFieldName {

    date, image, location, content, description, title, userinfo;

    public Class getRelatedClass() {
        switch (this) {
            case date:
                return Date.class;
            case content:
                return Content.class;
            case image:
                return Image.class;
            case description:
                return Description.class;
            case location:
                return Location.class;
            case title:
                return Title.class;
            case userinfo:
                return UserInfo.class;
            default: return RequestField.class;
        }
    }
}
