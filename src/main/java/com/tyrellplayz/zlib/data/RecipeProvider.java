package com.tyrellplayz.zlib.data;

import net.minecraft.advancements.criterion.EnterBlockTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.state.IProperty;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;

import java.util.Map;
import java.util.function.Consumer;

public abstract class RecipeProvider extends net.minecraft.data.RecipeProvider {

    public RecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected abstract void registerRecipes(Consumer<IFinishedRecipe> consumer);

    /**
     * Creates a new {@link EnterBlockTrigger} for use with recipe unlock criteria.
     */
    protected EnterBlockTrigger.Instance enteredBlock(Block blockIn) {
        return new EnterBlockTrigger.Instance(blockIn, null);
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having a certain amount of an item.
     */
    protected InventoryChangeTrigger.Instance hasItem(MinMaxBounds.IntBound amount, IItemProvider itemIn) {
        return this.hasItem(ItemPredicate.Builder.create().item(itemIn).count(amount).build());
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having a certain item.
     */
    protected InventoryChangeTrigger.Instance hasItem(IItemProvider itemIn) {
        return this.hasItem(ItemPredicate.Builder.create().item(itemIn).build());
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having an item within the given tag.
     */
    protected InventoryChangeTrigger.Instance hasItem(Tag<Item> tagIn) {
        return this.hasItem(ItemPredicate.Builder.create().tag(tagIn).build());
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having a certain item.
     */
    protected InventoryChangeTrigger.Instance hasItem(ItemPredicate... predicates) {
        return new InventoryChangeTrigger.Instance(MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, predicates);
    }

}
