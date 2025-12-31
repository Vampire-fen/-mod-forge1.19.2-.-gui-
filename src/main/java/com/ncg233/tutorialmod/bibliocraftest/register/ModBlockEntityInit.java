package com.ncg233.tutorialmod.bibliocraftest.register;

import com.ncg233.tutorialmod.BiblioCraft;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.ncg233.tutorialmod.bibliocraftest.blockentities.PaintingPressBlockEntity;

public class ModBlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTER=  DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BiblioCraft.MODID);

    public static final RegistryObject<BlockEntityType<PaintingPressBlockEntity>> PAINTING_PRESS_BLOCK_ENTITY =
            BLOCK_ENTITY_REGISTER.register("block_pating_press_entity",
                    ()-> BlockEntityType.Builder.of ((pos, state) -> new PaintingPressBlockEntity(pos, state), ModBlockInit.PAINTING_PRESS_BLOCK.get()).build(null));
}

