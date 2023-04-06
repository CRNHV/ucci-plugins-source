package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.runelite.api.Skill;
import net.unethicalite.api.game.Combat;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Prayers;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

import java.util.List;

public class DrinkPotions extends ZulrahTask
{
    private static final List<String> ANTIDOTES = List.of(
            "antidote", "anti-venom", "anti-venom+", "antipoison", "sanfew serum"
    );
    private static final List<String> PRAYER_RESTORES = List.of(
            "prayer potion", "super restore potion", "sanfew serum"
    );
    private static final List<String> RANGE_BOOSTS = List.of(
            "ranging potion", "bastion potion"
    );
    private static final List<String> MAGE_BOOSTS = List.of(
            "magic potion", "battlemage potion"
    );

    @Override
    public boolean validate()
    {
        return getZulrahCycle() != null
                && (canBoostRanged() || canBoostMagic() || canCurePoison() || canRestorePrayer());
    }

    @Override
    public int execute()
    {
        updateTask("Drinking potion");

        if (canCurePoison())
        {
            Inventory.getFirst(x -> x.hasAction("Drink")
                            && ANTIDOTES.stream().anyMatch(s -> x.getName().toLowerCase().contains(s)))
                    .interact("Drink");
            return 300;
        }

        if (canRestorePrayer())
        {
            Inventory.getFirst(x -> x.hasAction("Drink")
                            && PRAYER_RESTORES.stream().anyMatch(s -> x.getName().toLowerCase().contains(s)))
                    .interact("Drink");
            return 300;
        }

        if (canBoostRanged())
        {
            Inventory.getFirst(x -> x.hasAction("Drink")
                            && RANGE_BOOSTS.stream().anyMatch(s -> x.getName().toLowerCase().contains(s)))
                    .interact("Drink");
            return 300;
        }

        if (canBoostMagic())
        {
            Inventory.getFirst(x -> x.hasAction("Drink")
                            && MAGE_BOOSTS.stream().anyMatch(s -> x.getName().toLowerCase().contains(s)))
                    .interact("Drink");
        }

        return 300;
    }

    private boolean canBoostMagic()
    {
        return Skills.getBoostedLevel(Skill.MAGIC) == Skills.getLevel(Skill.MAGIC)
                && Inventory.contains(x -> x.hasAction("Drink")
                && MAGE_BOOSTS.stream().anyMatch(s -> x.getName().toLowerCase().contains(s)));
    }

    private boolean canBoostRanged()
    {
        return Skills.getBoostedLevel(Skill.RANGED) - Skills.getLevel(Skill.RANGED) <= 5
                && Inventory.contains(x -> x.hasAction("Drink")
                && RANGE_BOOSTS.stream().anyMatch(s -> x.getName().toLowerCase().contains(s)));
    }

    private boolean canRestorePrayer()
    {
        return Prayers.getPoints() <= 15
                && Inventory.contains(x -> x.hasAction("Drink")
                && PRAYER_RESTORES.stream().anyMatch(s -> x.getName().toLowerCase().contains(s)));
    }

    private boolean canCurePoison()
    {
        return Combat.isPoisoned()
                && Inventory.contains(x -> x.hasAction("Drink")
                && ANTIDOTES.stream().anyMatch(s -> x.getName().toLowerCase().contains(s)));
    }

    @Override
    public boolean isBlocking()
    {
        return false;
    }
}
