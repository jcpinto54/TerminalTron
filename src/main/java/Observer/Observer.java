package Observer;

public interface Observer<T> {
    void changed(T subject);
}
