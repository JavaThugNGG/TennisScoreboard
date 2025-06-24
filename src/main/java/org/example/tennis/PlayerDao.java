package org.example.tennis;

import org.hibernate.Session;

public class PlayerDao {
    public PlayerEntity getByName(Session session, String playerNameFilter) {
        return session.createQuery("""
                        FROM PlayerEntity
                        WHERE name = :name
                        """, PlayerEntity.class)
                .setParameter("name", playerNameFilter)
                .uniqueResult();
    }
}
