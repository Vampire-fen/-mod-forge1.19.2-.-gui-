package com.ncg233.tutorialmod.bibliocraftest.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.ncg233.tutorialmod.bibliocraftest.helper.PaintingUtil;

public class TextureStichHandler {
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        for (String resource : PaintingUtil.customArtResourceStrings)  {
            event.addSprite(ResourceLocation.parse(resource));
        }
    }

}

