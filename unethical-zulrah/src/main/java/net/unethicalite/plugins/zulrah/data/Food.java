package net.unethicalite.plugins.zulrah.data;

import lombok.Getter;

@Getter
public enum Food
{
    KARAMBWAN(Constants.KARAMBWAN),
    SHARK(Constants.SHARK);
    private final String name;

    Food(String name)
    {
        this.name = name;
    }
}
