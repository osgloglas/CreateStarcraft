package com.souls.starcraft.item;

import com.souls.starcraft.StarCraft;
import com.souls.starcraft.fluid.ModFluids;
import com.souls.starcraft.item.custom.StarWandItem;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(StarCraft.MODID);

    public static final DeferredItem<Item> AQUAMARINE = ITEMS.register("aquamarine", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STAR_WAND = ITEMS.register("star_wand", () -> new StarWandItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> STARMETAL = ITEMS.register("starmetal", () -> new Item(new Item.Properties()));

    public static final DeferredItem<BucketItem> LIQUID_STARLIGHT_BUCKET = ITEMS.register("liquid_starlight_bucket", () -> 
        new BucketItem(ModFluids.LIQUID_STARLIGHT.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
