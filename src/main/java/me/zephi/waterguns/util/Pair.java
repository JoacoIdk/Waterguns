package me.zephi.waterguns.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pair<K, T> {
    private K key;
    private T value;

    public boolean isComplete() {
        return key != null && value != null;
    }
}
