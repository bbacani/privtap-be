package hr.fer.dsd.privtap.model.requestField;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hr.fer.dsd.privtap.model.requestField.date.Date;
import hr.fer.dsd.privtap.model.requestField.image.Image;
import hr.fer.dsd.privtap.model.requestField.location.Location;
import hr.fer.dsd.privtap.model.requestField.textFields.Content;
import hr.fer.dsd.privtap.model.requestField.textFields.Description;
import hr.fer.dsd.privtap.model.requestField.textFields.Title;
import hr.fer.dsd.privtap.model.requestField.userInfo.UserInfo;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Content.class, name = "content"),
        @JsonSubTypes.Type(value = Description.class, name = "description"),
        @JsonSubTypes.Type(value = Title.class, name = "title"),
        @JsonSubTypes.Type(value = UserInfo.class, name = "userinfo"),
        @JsonSubTypes.Type(value = Location.class, name = "location"),
        @JsonSubTypes.Type(value = Image.class, name = "image"),
        @JsonSubTypes.Type(value = Date.class, name = "date"),
})
public abstract class RequestField {
    private RequestFieldName name;
    private Object value;
}
