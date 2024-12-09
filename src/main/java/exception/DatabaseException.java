package exception;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseException extends RuntimeException {
    private static final Logger LOGGER = Logger.getLogger(DatabaseException.class.getName());

    public DatabaseException(String message, SQLException e) {
        super(message, e);
        LOGGER.log(Level.SEVERE, message, e);
    }
}