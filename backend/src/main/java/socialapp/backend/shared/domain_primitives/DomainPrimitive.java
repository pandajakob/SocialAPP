package socialapp.backend.shared.domain_primitives;

public class DomainPrimitive<T> {
    private T value;

    public DomainPrimitive(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }
}
