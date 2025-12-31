package com.ncg233.tutorialmod.bibliocraftest.container;

import com.ncg233.tutorialmod.bibliocraftest.blockentities.PaintingPressBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import com.ncg233.tutorialmod.bibliocraftest.register.MenuRegister;

public class PaintingPressContainerMenu extends AbstractContainerMenu {
    PaintingPressBlockEntity blockEntity;
    public PaintingPressContainerMenu(int windowId, BlockPos pos, Inventory inventory, Player player) {
    super(MenuRegister.PAINTING_PRESS_CONTAINER_MENU.get(),windowId);
    System.out.println("Menu---------------------------------------------------------");
    if (player.getCommandSenderWorld().getBlockEntity(pos)instanceof PaintingPressBlockEntity entity)
        blockEntity = entity;
    }

    public PaintingPressContainerMenu(int windowId, Inventory playerInv) {
        this(windowId, BlockPos.ZERO, playerInv, playerInv.player);
        System.out.println("kkkkkkkkkkkk------------------------");
    }

    @Override
    public ItemStack quickMoveStack(Player player, int sourceSlotIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    public PaintingPressBlockEntity getEntity() {
        return blockEntity;
    }
}
