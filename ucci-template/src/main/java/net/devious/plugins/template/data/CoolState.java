package net.devious.plugins.template.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CoolState
{
    COOL(1,true),
    NOT_COOL(2,false);

    private final int coolIndex;
    private final boolean someBool;
}
