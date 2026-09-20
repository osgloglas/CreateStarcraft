package com.souls.starcraft.menu;

import java.util.ArrayList;
import java.util.List;

import com.souls.starcraft.ModDataComponents;
import com.souls.starcraft.ModMenuTypes;
import com.souls.starcraft.ModTags;
import com.souls.starcraft.spell.StarWandSpells;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class StarWandMenu extends AbstractContainerMenu {
    private final SimpleContainer spellContainer = new SimpleContainer(36);
    private final InteractionHand wandHand;
    private final ItemStack wandStack;
    
    public StarWandMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readEnum(InteractionHand.class));
    }

    public StarWandMenu(int containerId, Inventory inventory, InteractionHand hand) {
        super(ModMenuTypes.STAR_WAND.get(), containerId);

        this.wandHand = hand;
        this.wandStack = inventory.player.getItemInHand(hand);

        StarWandSpells spells = wandStack.get(ModDataComponents.STAR_WAND_SPELLS.get());

        if (spells == null) {
            spells = new StarWandSpells();

            wandStack.set(ModDataComponents.STAR_WAND_SPELLS.get(), spells);
        }

        ItemContainerContents glasses = wandStack.get(DataComponents.CONTAINER);

        if (glasses == null) {
            glasses = ItemContainerContents.EMPTY;

            wandStack.set(DataComponents.CONTAINER, glasses);
        }

        List<ItemStack> savedGlasses = glasses.stream().toList();

        for (int i = 0; i < Math.min(36, savedGlasses.size()); i++) {
            spellContainer.setItem(i, savedGlasses.get(i).copy());
        }

        int [] spellRowY = {19, 41, 63};

        for (int spell = 0; spell < 3; spell++) {
            for (int socket = 0; socket < 12; socket++) {
                final int slotIndex = (spell * 12) + socket;

                this.addSlot(new Slot(spellContainer, slotIndex, 22 + (socket * 18), spellRowY[spell]) {
                    @Override 
                    public boolean mayPlace(ItemStack stack) {
                        return stack.is(ModTags.CONSTELLATIONS);
                    }

                    @Override 
                    public int getMaxStackSize() {
                        return 1;
                    }

                    @Override 
                    public void setChanged() {
                        super.setChanged();

                        List<ItemStack> stacks = new ArrayList<>();

                        for (int i = 0; i < 36; i++) {
                            stacks.add(spellContainer.getItem(i).copy());
                        }

                        wandStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(stacks));
                    }
                });
            }
        }

        //inventory
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(inventory, column + row * 9 + 9, 40 + column * 18, 107 + row * 18));
            }
        }

        //hotbar
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(inventory, column, 40 + column * 18, 165));
        }
    }

    @Override 
    public boolean stillValid(Player player) {
        return true;
    }

    @Override 
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
