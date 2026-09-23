package com.souls.starcraft.item;

import com.souls.starcraft.ModDataComponents;
import com.souls.starcraft.StarCraft;
import com.souls.starcraft.fluid.ModFluids;
import com.souls.starcraft.item.custom.ConstellationPaperItem;
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
    public static final DeferredItem<Item> STAR_WAND = ITEMS.register("star_wand", () -> 
        new StarWandItem(new Item.Properties().stacksTo(1).component(ModDataComponents.SELECTED_SPELL, 0)));
    public static final DeferredItem<Item> STARMETAL = ITEMS.register("starmetal", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CONSTELLATION_PAPER = ITEMS.register("constellation_paper", () -> new ConstellationPaperItem(new Item.Properties()));

    public static final DeferredItem<Item> LENS = ITEMS.register("lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LIBELLULA_LENS = ITEMS.register("libellula_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CERVUS_LENS = ITEMS.register("cervus_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LUCERNARIUS_LENS = ITEMS.register("lucernarius_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BALAENA_STELLARIS_LENS = ITEMS.register("balaena_stellaris_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LUNAE_GEMINAE_LENS = ITEMS.register("lunae_geminae_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CORONA_CRYSTALLINA_LENS = ITEMS.register("corona_crystallina_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VULPES_LENS = ITEMS.register("vulpes_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MAGNA_FERRIVIA_LENS = ITEMS.register("magna_ferrivia_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PAPILIO_LENS = ITEMS.register("papilio_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GLADIUS_FRACTUS_LENS = ITEMS.register("gladius_fractus_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ARCUS_STELLARIS_LENS = ITEMS.register("arcus_stellaris_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PORTA_ASTRALIS_LENS = ITEMS.register("porta_astralis_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STELLA_POLARIS_LENS = ITEMS.register("stella_polaris_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PEGASUS_LENS = ITEMS.register("pegasus_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CUBUS_LENS = ITEMS.register("cubus_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HOROLOGIUM_LENS = ITEMS.register("horologium_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPIRALIS_LENS = ITEMS.register("spiralis_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CATENA_GALACTICA_LENS = ITEMS.register("catena_galactica_lens", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STRENUUS_FAUTOR_LENS = ITEMS.register("strenuus_fautor_lens", () -> new Item(new Item.Properties()));

    public static final DeferredItem<BucketItem> LIQUID_STARLIGHT_BUCKET = ITEMS.register("liquid_starlight_bucket", () -> 
        new BucketItem(ModFluids.LIQUID_STARLIGHT.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
