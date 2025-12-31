package com.ncg233.tutorialmod.bibliocraftest.register;

import com.ncg233.tutorialmod.BiblioCraft;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.ncg233.tutorialmod.bibliocraftest.blocks.BlockPaintingPress;

public class ModBlockInit {
    public static final DeferredRegister <Block> BLOCKREGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, BiblioCraft.MODID);
    public static final RegistryObject<Block> OBSIDIAN_BLOCK=BLOCKREGISTER.register("obsidian_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PAINTING_PRESS_BLOCK=BLOCKREGISTER.register("painting_press",()-> new BlockPaintingPress(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

}
