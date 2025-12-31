package com.ncg233.tutorialmod.bibliocraftest.helper;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BiblioCreativeTab {
    public static final String TAB_NAME = "Biblio";

    public static final CreativeModeTab BIBLIO_ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.DIAMOND);
        }
    };

}
