package ru.bekhterev.sservice.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.time.Year;
import java.util.stream.Stream;

public class RecordBookNumberGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String year = Year.now().toString();
        String query = String.format("SELECT %s FROM %s",
                session.getEntityPersister(object.getClass().getName(), object)
                        .getIdentifierPropertyName(), object.getClass().getSimpleName());
        Stream<String> ids = session.createQuery(query).stream();
        long max = ids.map(o -> o.replace(year, ""))
                .mapToLong(Long::parseLong)
                .max()
                .orElse(0L);
        return String.format("%s%03d", year, max + 1);
    }
}
