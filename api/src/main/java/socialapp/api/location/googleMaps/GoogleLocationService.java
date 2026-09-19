package socialapp.api.location.googleMaps;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import socialapp.api.location.googleMaps.DTO.GoogleGeocodeResponse;
import socialapp.api.location.Location;
import socialapp.api.location.googleMaps.exceptions.GoogleApiCallException;
import socialapp.api.location.googleMaps.exceptions.GoogleLocationNotFoundException;

@Service
public class GoogleLocationService {
    private final RestClient restClient;
    private final GoogleProperties googleProperties;

    public GoogleLocationService(GoogleProperties googleProperties) {
        this.restClient = RestClient.builder()
                .baseUrl(googleProperties.getGoogleMapsApiBaseUrl())
                .build();
        this.googleProperties = googleProperties;
    }

    public Location getFullLocation(double latitude, double longitude) {

        GoogleGeocodeResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v4/geocode/location")
                        .queryParam("location.latitude", latitude)
                        .queryParam("location.longitude", longitude)
                        .queryParam("key", googleProperties.getGoogleMapsApiKey())

                        .build())
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        (req, res) -> {
                            throw new GoogleApiCallException(
                                    "Google Geocoding failed with status " + res.getStatusCode()
                            );
                        }
                )
                .body(GoogleGeocodeResponse.class);

        if (response == null || response.results() == null || response.results().isEmpty()) {
            throw new GoogleLocationNotFoundException("Could not find google location");
        }
        var result = response.results().get(0);

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
