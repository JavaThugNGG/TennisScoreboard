package org.example.tennis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MatchPersistenceService {
    public PlayersResultDto persist(SessionFactory sessionFactory, int firstPlayerId, int secondPlayerId, PlayerSide winner) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            PlayerEntity firstPlayer = session.get(PlayerEntity.class, firstPlayerId);
            PlayerEntity secondPlayer = session.get(PlayerEntity.class, secondPlayerId);

            MatchEntity matchEntity = createMatch(firstPlayer, secondPlayer, winner);

            session.persist(matchEntity);
            session.getTransaction().commit();

            return determineResults(winner);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    private MatchEntity createMatch(PlayerEntity firstPlayer, PlayerEntity secondPlayer, PlayerSide winner) {
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

    private PlayersResultDto determineResults(PlayerSide winner) {
        if (winner == PlayerSide.FIRST) {
            return new PlayersResultDto(PlayerResult.WINNER, PlayerResult.LOSER);
        } else {
            return new PlayersResultDto(PlayerResult.LOSER, PlayerResult.WINNER);
        }
    }
}


