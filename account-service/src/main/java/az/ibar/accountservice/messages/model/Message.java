package az.ibar.accountservice.messages.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message<T> {
    private Long id;
    private String type;
    private T data;

    public Message(Long id, T data, String type) {
        this.id = id;
        this.data = data;
        this.type = type;
    }
}
