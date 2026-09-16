package by.tami.skateservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_skate")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Skate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skate_identity_number", unique = true, nullable = false)
    private Long skateIdentityNumber;

    @Column(nullable = false)
    private Short size;

    @Column(nullable = false)
    private Sex sex;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = Boolean.TRUE;
}
