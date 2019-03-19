package hmggvcmob.entity.friend;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;

public interface ISolidier extends IAnimals
{
    /** Entity selector for IMob types. */
    IEntitySelector mobSelector = new IEntitySelector()
    {
        /**
         * Return whether the specified entity is applicable to this filter.
         */
        public boolean isEntityApplicable(Entity p_82704_1_)
        {
            return p_82704_1_ instanceof ISolidier;
        }
    };
}