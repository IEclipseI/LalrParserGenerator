package util;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

@Value
@EqualsAndHashCode
public class Pair<K, V> implements Serializable {
    K key;
    V value;
}
