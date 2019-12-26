package util;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class Pair<K, V> {
    K key;
    V value;
}
