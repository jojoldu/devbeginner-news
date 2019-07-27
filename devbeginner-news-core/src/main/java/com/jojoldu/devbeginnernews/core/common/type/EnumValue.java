package com.jojoldu.devbeginnernews.core.common.type;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnumValue implements EnumType{

    private String id;
    private String title;

    public EnumValue(EnumType enumType) {
        id = enumType.getId();
        title = enumType.getTitle();
    }
}
