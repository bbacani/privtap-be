package hr.fer.dsd.privtap.model.requestField.location;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.requestField.RequestFieldType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location extends RequestField {


    private class Coordinates {
        private double latitude;
        private double longitude;

        public Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    public Location buildDefault(RequestFieldType type, String maskedValue) {
        this.setType(type);
        this.setMaskedValue(maskedValue);
        return this;
    }
}
