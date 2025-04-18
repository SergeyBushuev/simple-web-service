package ru.practicum.BootBlogApp.utils;

import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateNewTagsTrigger implements Trigger {
    @Override
    public void init(Connection connection, String s, String s1, String s2, boolean b, int i) throws SQLException {
    }

    @Override
    public void fire(Connection connection, Object[] oldRow, Object[] newRow) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO tags VALUES(?) ON CONFLICT DO NOTHING")) {
            ps.setObject(1, newRow[1]);
            ps.execute();
        }
    }

    @Override
    public void close() throws SQLException {
    }

    @Override
    public void remove() throws SQLException {
    }
}
