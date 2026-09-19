package socialapp.backend.location;

import org.springframework.stereotype.Service;
import socialapp.backend.location.googleMaps.GoogleLocationService;
import socialapp.backend.location.googleMaps.GoogleProperties;


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

