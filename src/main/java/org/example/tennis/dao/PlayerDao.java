package org.example.tennis.dao;

import org.example.tennis.entity.PlayerEntity;
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
