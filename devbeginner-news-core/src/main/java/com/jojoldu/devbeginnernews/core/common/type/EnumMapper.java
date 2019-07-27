package com.jojoldu.devbeginnernews.core.common.type;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EnumMapper {
    private Map<String, List<EnumValue>> factory = new LinkedHashMap<>();

    private List<EnumValue> toEnumValues(Class<? extends EnumType> e){
        return Arrays
                .stream(e.getEnumConstants())
                .map(EnumValue::new)
                .collect(Collectors.toList());
    }

    public void put(Class<? extends EnumType> e){
        factory.put(e.getName(), toEnumValues(e));
    }

    public void put(String key, Class<? extends EnumType> e){
        factory.put(key, toEnumValues(e));
    }

    public Map<String, List<EnumValue>> getAll(){
        return factory;
    }

    public List<EnumValue> getOne(Class<? extends EnumType> e){
        return factory.get(e.getName());
    }

    public Map<String, List<EnumValue>> gets(String keys){
        return Arrays.stream(keys.split(","))
                .collect(Collectors.toMap(Function.identity(), key -> factory.get(key)));
    }

    public List<EnumValue> getOne(String key){
        return factory.get(key);
    }

}