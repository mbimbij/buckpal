package io.reflectoring.buckpal.generic;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE) @AllArgsConstructor
@ToString
@EqualsAndHashCode
public class EntityId {
    String id;
}
