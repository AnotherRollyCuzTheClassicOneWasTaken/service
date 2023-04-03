package ro.unibuc.hello.data;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Generated;

import org.springframework.data.annotation.Id;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class UserEntity {
    @Id
    private String id;

    private String email;
    private String parola;
}
