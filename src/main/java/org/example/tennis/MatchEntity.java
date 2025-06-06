package org.example.tennis;

import jakarta.persistence.*;

@Entity
@Table(name="matches")
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

    public MatchEntity(PlayerEntity player1, PlayerEntity player2, PlayerEntity winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }

    public MatchEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlayerEntity getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayerEntity player1) {
        this.player1 = player1;
    }

    public PlayerEntity getPlayer2() {
        return player2;
    }

    public void setPlayer2(PlayerEntity player2) {
        this.player2 = player2;
    }

    public PlayerEntity getWinner() {
        return winner;
    }

    public void setWinner(PlayerEntity winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "MatchEntity{" +
                "id=" + id +
                ", player1=" + (player1 != null ? player1.getName() : "null") +
                ", player2=" + (player2 != null ? player2.getName() : "null") +
                ", winner=" + (winner != null ? winner.getName() : "null") +
                '}';
    }
}
