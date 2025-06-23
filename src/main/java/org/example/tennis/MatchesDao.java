package org.example.tennis;

import org.hibernate.Session;
import java.util.List;

public class MatchesDao {

    List<MatchEntity> getPageOfMatches(Session session, int startIndex) {         //подумаешь как можно получше назвать
        return session.createQuery("""
                            FROM MatchEntity
                            ORDER BY id DESC
                            """, MatchEntity.class)
                .setFirstResult(startIndex)
                .setMaxResults(5)
                .getResultList();
    }

    long getTotalMatches(Session session) {
        return session.createQuery("""
                    SELECT COUNT(m)
                    FROM MatchEntity m
                    """, Long.class).uniqueResult();
    }

    PlayerEntity getPlayerByName(Session session, String playerNameFilter) {
        return session.createQuery("""
                            FROM PlayerEntity
                            WHERE name = :name
                            """, PlayerEntity.class)
                .setParameter("name", playerNameFilter)
                .uniqueResult();
    }

    List<MatchEntity> getPageOfMatchesWithPlayerNameFilter(Session session, PlayerEntity player, int startIndex) {
        return session.createQuery("""
                                    FROM MatchEntity
                                    WHERE player1 = :player or player2 = :player
                                    ORDER BY id DESC
                                    """, MatchEntity.class)
                .setParameter("player", player)
                .setFirstResult(startIndex)
                .setMaxResults(5)
                .getResultList();
    }

    long getTotalMatchesWithPlayerNameFilter(Session session, PlayerEntity player) {
        return session.createQuery("""
                    SELECT COUNT(m)
                    FROM MatchEntity m
                    WHERE player1 = :player or player2 = :player
                    """, Long.class).setParameter("player", player).getSingleResult();
    }
}
