package org.example.tennis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MatchFinishingService {

    public PlayersResultDto persistMatch(SessionFactory sessionFactory, MatchScoreModel match, PlayerSide winner) {
        int firstPlayerId = match.getFirstPlayerId();
        int secondPlayerId = match.getSecondPlayerId();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            PlayerEntity firstPlayer = session.get(PlayerEntity.class, firstPlayerId);
            PlayerEntity secondPlayer = session.get(PlayerEntity.class, secondPlayerId);

            MatchEntity matchEntity = buildMatch(firstPlayer, secondPlayer, winner);

            session.persist(matchEntity);
            session.getTransaction().commit();

            return determinePlayersResult(winner);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    private MatchEntity buildMatch(PlayerEntity firstPlayer, PlayerEntity secondPlayer, PlayerSide winner) {
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setPlayer1(firstPlayer);
        matchEntity.setPlayer2(secondPlayer);

        if (winner == PlayerSide.FIRST) {
            matchEntity.setWinner(firstPlayer);
        } else {
            matchEntity.setWinner(secondPlayer);
        }

        return matchEntity;
    }

    private PlayersResultDto determinePlayersResult(PlayerSide winner) {
        if (winner == PlayerSide.FIRST) {
            return new PlayersResultDto("WIN", "LOS");
        } else {
            return new PlayersResultDto("LOS", "WIN");
        }
    }
}


