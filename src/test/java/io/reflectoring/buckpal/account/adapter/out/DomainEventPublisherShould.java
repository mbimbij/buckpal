package io.reflectoring.buckpal.account.adapter.out;

import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.CustomerId;
import io.reflectoring.buckpal.account.service.creation.AccountCreatedEvent;
import io.reflectoring.buckpal.account.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import static org.assertj.core.api.Assertions.assertThat;

class DomainEventPublisherShould {
    @Test
    void notifyEventStore_whenSubscribed() {
        // GIVEN
        Pair<IPublishDomainEvents, InMemoryEventStore> pair = TestUtils.createInMemoryEventPublisherAndEventStore();
        InMemoryEventStore eventStore = pair.getSecond();
        IPublishDomainEvents domainEventPublisher = pair.getFirst();

        // WHEN
        AccountId accountId = new AccountId("id");
        AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(accountId, new CustomerId("id"));
        domainEventPublisher.publish(accountCreatedEvent);

        // THEN
        assertThat(eventStore.getAll()).containsExactly(accountCreatedEvent);
    }
}