package socialapp.backend.shared.domain_primitives;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumber extends DomainPrimitive<String>{
    public PhoneNumber(String value){
        super(value);

        List<Character> allowedCharacters = List.of('0','1','2','3','4','5','6','7','8','9','+');

        if (value.length()<6) {
            throw new IllegalArgumentException("invalid phone number");
        }

        for (int i = 0; i < value.length(); i++){
            char c = value.charAt(i);
            if (!allowedCharacters.contains(c)){
                throw new IllegalArgumentException("invalid phone number");
            }
        }
    }
}
