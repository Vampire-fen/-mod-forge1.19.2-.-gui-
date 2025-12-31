package com.ncg233.tutorialmod.bibliocraftest.register;

import com.ncg233.tutorialmod.BiblioCraft;
import com.ncg233.tutorialmod.bibliocraftest.container.PaintingPressContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegister {
   public static final DeferredRegister<MenuType<?>> MENU_REGISTER=DeferredRegister.create(ForgeRegistries.MENU_TYPES, BiblioCraft.MODID);
   public static final RegistryObject<MenuType<PaintingPressContainerMenu>>PAINTING_PRESS_CONTAINER_MENU =MENU_REGISTER.register("painting_press_container_menu",
           () ->IForgeMenuType.create(
                   (windowId, inv, data) -> new PaintingPressContainerMenu(windowId,data.readBlockPos(),inv,inv.player)));
}

