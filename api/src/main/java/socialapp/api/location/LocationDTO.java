package socialapp.api.location;

public record LocationDTO(
    Double longitude,
    Double latitude,
    String country,
    String city,
    String formattedAddress
){}
