package hr.fer.dsd.privtap.model.requestField.location;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Location extends RequestField {

    public Location(Coordinates coordinates) {
        this.setName("location");
        this.setValue(coordinates);
    }


    private class Coordinates {
        private double latitude;
        private double longitude;

        public Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
