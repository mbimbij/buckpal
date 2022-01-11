package io.reflectoring.buckpal.account.utils;

import io.reflectoring.buckpal.account.adapter.out.DomainEventPublisher;
import io.reflectoring.buckpal.account.adapter.out.InMemoryEventStore;
import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;
import org.springframework.data.util.Pair;

public class TestUtils {
    public static Pair<IPublishDomainEvents, InMemoryEventStore> createInMemoryEventPublisherAndEventStore() {
        DomainEventPublisher eventPublisher = new DomainEventPublisher();
        InMemoryEventStore eventStore = new InMemoryEventStore();
        eventPublisher.subscribe(eventStore);
        return Pair.of(eventPublisher, eventStore);
    }
}
