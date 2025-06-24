package org.example.tennis;

import org.hibernate.Session;

import java.util.List;

public class MatchDao {

    public List<MatchEntity> getPage(Session session, int startIndex) {
        return session.createQuery("""
                        FROM MatchEntity
                        ORDER BY id DESC
                        """, MatchEntity.class)
                .setFirstResult(startIndex)
                .setMaxResults(5)
                .getResultList();
    }

    public long count(Session session) {
        return session.createQuery("""
                SELECT COUNT(m)
                FROM MatchEntity m
                """, Long.class).uniqueResult();
    }

    public List<MatchEntity> getPageWithPlayerFilter(Session session, PlayerEntity player, int startIndex) {
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

    public long countWithPlayerFilter(Session session, PlayerEntity player) {
        return session.createQuery("""
                SELECT COUNT(m)
                FROM MatchEntity m
                WHERE player1 = :player or player2 = :player
                """, Long.class).setParameter("player", player).getSingleResult();
    }
}
