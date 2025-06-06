package org.example.tennis;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="players")
public class PlayerEntity {
    @Id
    @GeneratedValue
    private int id;
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

    public PlayerEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayer1(MatchEntity match) {
        matchesAsPlayer1.add(match);
        match.setPlayer1(this);
    }

    public void setPlayer2(MatchEntity match) {
        matchesAsPlayer2.add(match);
        match.setPlayer2(this);
    }

    public void setWinner(MatchEntity match) {
        matchesWon.add(match);
        match.setWinner(this);
    }

    public List<MatchEntity> getMatchesAsPlayer1() {
        return matchesAsPlayer1;
    }

    public List<MatchEntity> getMatchesAsPlayer2() {
        return matchesAsPlayer2;
    }

    public List<MatchEntity> getMatchesWon() {
        return matchesWon;
    }
}