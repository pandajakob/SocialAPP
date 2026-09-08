package socialapp.backend.Location;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import socialapp.backend.config.GoogleConfig;

import java.util.List;

@Service
public class LocationService {

    private final RestClient restClient;
    private final GoogleConfig googleConfig;

    public LocationService(GoogleConfig googleConfig) {
        this.googleConfig = googleConfig;

        this.restClient = RestClient.builder()
                .baseUrl(googleConfig.getGoogleMapsApiBaseUrl())
                .build();
    }

    public Location createLocation(double latitude, double longitude) {

        GoogleGeocodeResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v4/geocode/location")
                        .queryParam("location.latitude", latitude)
                        .queryParam("location.longitude", longitude)
                        .queryParam("key", googleConfig.getGoogleMapsApiKey())
                        .build())
                .retrieve()
                .body(GoogleGeocodeResponse.class);

        if (response == null || response.results() == null || response.results().isEmpty()) {
            throw new RuntimeException("Could not find location");
        }

        var result = response.results().get(0);
        System.out.println("RESULT: " + response.results());
        result.addressComponents().forEach(System.out::println);

        Location location = new Location();
        location.setCoordinates(extractLocationPoint(longitude, latitude));
        location.setFormattedAddress(result.formattedAddress());

        for (var component : result.addressComponents()) {
            if (component.types().contains("country")) {
                location.setCountry(component.longText());
                location.setCountryCode(component.shortText());
            }

            if (component.types().contains("locality")) {
                location.setCity(component.longText());
            }

            if (component.types().contains("postal_code")) {
                location.setPostalCode(component.longText());
            }
        }

        return location;
    }

    private Point extractLocationPoint(double longitude, double latitude) {
        GeometryFactory factory = new GeometryFactory();
        return factory.createPoint(new Coordinate(longitude, latitude));
    }
}

record GoogleGeocodeResponse(
        List<GoogleResult> results
) {}

record GoogleResult(
        String formattedAddress,
        List<GoogleAddressComponent> addressComponents
) {}

record GoogleAddressComponent(
        String longText,
        String shortText,
        List<String> types
) {}