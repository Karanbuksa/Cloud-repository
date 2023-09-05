package my.diplomaproject.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class File {
    @Id
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private CloudUser cloudUser;

    @Column
    private String path;

    @Column
    private String name;
}
