package az.ibar.notificationservice.messages.model;

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

    public Message(T data, String type) {
        this.type = type;
        this.data = data;
    }

}
