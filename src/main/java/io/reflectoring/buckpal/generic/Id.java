package io.reflectoring.buckpal.generic;

import java.util.UUID;
import java.util.function.Supplier;

public class Id extends WrappedPrimitive<String> {
    public Id(String value) {
        super(value);
    }

    private static Supplier<Id> idSupplier = () -> new Id(UUID.randomUUID().toString());

    public static Id generateNextId() {
        return idSupplier.get();
    }

    public static void setIdSupplier(Supplier<Id> idSupplier) {
        Id.idSupplier = idSupplier;
    }
}
