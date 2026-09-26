package com.souls.starcraft.menu;

import com.souls.starcraft.ModMenuTypes;
import com.souls.starcraft.block.custom.entity.StarlightCrafterBlockEntity;
import com.souls.starcraft.recipe.ModRecipes;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;

public class StarlightCrafterMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final StarlightCrafterBlockEntity blockEntity;
    private final ResultContainer resultContainer = new ResultContainer();
    private final TransientCraftingContainer craftingContainer = new TransientCraftingContainer(this, 3, 3);

    public StarlightCrafterMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf buffer) {
        this(containerId, playerInventory, (StarlightCrafterBlockEntity) playerInventory.player.level().getBlockEntity(buffer.readBlockPos()));
    }

    public StarlightCrafterMenu(int containerId, Inventory playerInventory, StarlightCrafterBlockEntity blockEntity) {
        super(ModMenuTypes.STARLIGHT_CRAFTER.get(), containerId);

        this.blockEntity = blockEntity;
        this.access = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        addCraftingSlots();
        addResultSlot(playerInventory.player);
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private void addCraftingSlots() {
        int startX = 30;
        int startY = 17;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                int slot = column + row * 3;

                this.addSlot(new Slot(craftingContainer, slot, startX + column * 18, startY + row * 18));
            }
        }
    }

    private void addResultSlot(Player player) {
        this.addSlot(new ResultSlot(player, craftingContainer, resultContainer, 0, 124, 35));
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }
    }

    @Override 
    public void slotsChanged(Container container) {
        super.slotsChanged(container);

        if (!(blockEntity.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

        CraftingInput input = craftingContainer.asCraftInput();

        var recipe = serverLevel.getRecipeManager().getRecipeFor(ModRecipes.STARLIGHT_CRAFTING_TYPE.get(), input, serverLevel);

        if (recipe.isPresent()) {
            ItemStack result = recipe.get().value().assemble(input, serverLevel.registryAccess());

            resultContainer.setItem(0, result);
        } else {
            resultContainer.setItem(0, ItemStack.EMPTY);
        }

        broadcastChanges();
    }

    @Override 
    public void removed(Player player) {
        super.removed(player);

        this.access.execute((level, pos) -> {
            this.clearContainer(player, craftingContainer);
        });
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
