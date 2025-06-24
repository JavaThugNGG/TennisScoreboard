package org.example.tennis;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
public class MatchEntity {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "player1")
    private PlayerEntity player1;
    @ManyToOne
    @JoinColumn(name = "player2")
    private PlayerEntity player2;
    @ManyToOne
    @JoinColumn(name = "winner")
    private PlayerEntity winner;
}
