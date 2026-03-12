package socialapp.backend.utils.domain_primitives;

public class Password {
    public String password;
    public Password(String password){
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
