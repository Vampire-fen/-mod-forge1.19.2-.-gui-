package com.ncg233.tutorialmod;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import com.ncg233.tutorialmod.bibliocraftest.gui.PaintingPressScreen;
import com.ncg233.tutorialmod.bibliocraftest.register.MenuRegister;
import com.ncg233.tutorialmod.bibliocraftest.register.ModBlockEntityInit;
import com.ncg233.tutorialmod.bibliocraftest.register.ModBlockInit;
import com.ncg233.tutorialmod.bibliocraftest.register.ModItemInit;

@Mod(BiblioCraft.MODID)
public class BiblioCraft
{

    public static final String MODID = "bibliocraft";
    public static BiblioCraft instance;

    public BiblioCraft(FMLJavaModLoadingContext context)
    {
        instance=this;
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);
        ModItemInit.ITEM_REGISTER.register(modEventBus);
        ModBlockInit.BLOCKREGISTER.register(modEventBus);
        ModBlockEntityInit.BLOCK_ENTITY_REGISTER.register(modEventBus);
        MenuRegister.MENU_REGISTER.register(modEventBus);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {

                    PaintingPressScreen.vanillaArtList = ForgeRegistries.PAINTING_VARIANTS.getValues().toArray(new PaintingVariant[0]);

                    int o = 0;
                    for (PaintingVariant variant : PaintingPressScreen.vanillaArtList) {
                        PaintingPressScreen.vanillaArtListName[o] = ForgeRegistries.PAINTING_VARIANTS.getKey(variant).getPath();
                        o++;
                    }
                });

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @OnlyIn(Dist.CLIENT)
    private void onClientSetup(FMLClientSetupEvent event) {
        //这两个输出终端能看见
        System.out.println("outer_-------------------------------------------------------------------------------------------");
        event.enqueueWork(() -> {
            System.out.println("inter_-------------------------------------------------------------------------------------------");
            MenuScreens.register(
                    MenuRegister.PAINTING_PRESS_CONTAINER_MENU.get(),
                    PaintingPressScreen::new);

        });
    }
}

