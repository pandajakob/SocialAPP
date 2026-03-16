package socialapp.backend.shared.domain_primitives;


public class PhotoURL extends DomainPrimitive<String> {
    private String url;

    public PhotoURL(String value) {
        super(value);
    }

}