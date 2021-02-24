package az.ibar.stockservice.messages.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message<T> {
    private Long id;
    private String type;
    private T data;

    public Message(T data, String type) {
        this.data = data;
        this.type = type;
    }
}
