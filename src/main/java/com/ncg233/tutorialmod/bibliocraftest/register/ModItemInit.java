package com.ncg233.tutorialmod.bibliocraftest.register;

import com.ncg233.tutorialmod.BiblioCraft;
import com.ncg233.tutorialmod.ObsidianIngot;
import com.ncg233.tutorialmod.bibliocraftest.helper.BiblioCreativeTab;
import com.ncg233.tutorialmod.bibliocraftest.items.ItemPaintingCanvas;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemInit {
    public static final DeferredRegister<Item> ITEM_REGISTER=  DeferredRegister.create(ForgeRegistries.ITEMS, BiblioCraft.MODID);
    public static final RegistryObject<Item> OBSIDIAN_ITEM =ITEM_REGISTER.register("obsidian_ingot",()-> new ObsidianIngot() );
    public static final RegistryObject<Item> ITEM_REGISTRY_OBJECT =ITEM_REGISTER.register("item_painting_canvas",()-> new ItemPaintingCanvas() );
    public static final RegistryObject<Item> OBSIDIAN_BLODK_ITEM=fromBlock(ModBlockInit.OBSIDIAN_BLOCK);
    public static final RegistryObject<Item> PAINTING_PRESS_ITEM=fromBlock(ModBlockInit.PAINTING_PRESS_BLOCK);

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEM_REGISTER.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(BiblioCreativeTab.BIBLIO_ITEM_GROUP)));
    }

}