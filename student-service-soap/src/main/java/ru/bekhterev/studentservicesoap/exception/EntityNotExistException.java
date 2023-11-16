package ru.bekhterev.studentservicesoap.exception;

public class EntityNotExistException extends RuntimeException {

    public EntityNotExistException(String format, Object... args) {
        super(String.format(format, args));
    }
}
