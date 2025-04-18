package ru.practicum.BootBlogApp.utils;

import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRedundantTagsTrigger implements Trigger {
    @Override
    public void init(Connection connection, String s, String s1, String s2, boolean b, int i) throws SQLException {
        Trigger.super.init(connection, s, s1, s2, b, i);
    }

    @Override
    public void fire(Connection connection, Object[] oldRow, Object[] newRow) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM tags " +
                        "    WHERE NOT EXISTS ( " +
                        "        SELECT FROM post_tags " +
                        "        WHERE post_tags.tag_id = ? " +
                        "    ) AND ? = tags.tag;")) {
            ps.setObject(1, oldRow[1]);
            ps.setObject(2, oldRow[1]);
            ps.execute();
        }
    }

    @Override
    public void close() throws SQLException {}

    @Override
    public void remove() throws SQLException {}
}
