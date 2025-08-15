package org.example.tennis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
public class PlayerEntity {
    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "player1")
    private List<MatchEntity> matchesAsPlayer1 = new ArrayList<>();

    @OneToMany(mappedBy = "player2")
    private List<MatchEntity> matchesAsPlayer2 = new ArrayList<>();

    @OneToMany(mappedBy = "winner")
    private List<MatchEntity> matchesWon = new ArrayList<>();

    public PlayerEntity(String name) {
        this.name = name;
    }
}