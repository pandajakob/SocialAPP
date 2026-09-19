package socialapp.api.location;

import org.springframework.stereotype.Service;
import socialapp.api.location.googleMaps.GoogleLocationService;
import socialapp.api.location.googleMaps.GoogleProperties;


@Service
public class LocationService {

    private final GoogleLocationService googleLocationService;

    public LocationService(GoogleProperties googleProperties, GoogleLocationService googleLocationService) {

        this.googleLocationService = googleLocationService;
    }

    public Location createLocation(double latitude, double longitude) {
        return googleLocationService.getFullLocation(latitude, longitude);
    }


}

