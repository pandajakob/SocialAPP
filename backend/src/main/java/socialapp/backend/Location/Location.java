package socialapp.backend.Location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Entity
public class Location {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(columnDefinition = "geometry(Point,4326)", nullable = false)
    private Point coordinates;

    @Column(nullable = false)
    private String country;
    private String countryCode;
    @Column(nullable = false)
    private String city;
    private String postalCode;
    private String formattedAddress;

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public UUID getId() {
        return id;
    }
}
