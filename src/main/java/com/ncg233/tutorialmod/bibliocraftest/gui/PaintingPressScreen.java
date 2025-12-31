package com.ncg233.tutorialmod.bibliocraftest.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Inventory;
import com.ncg233.tutorialmod.CommonProxy;
import com.ncg233.tutorialmod.bibliocraftest.blockentities.PaintingPressBlockEntity;
import com.ncg233.tutorialmod.bibliocraftest.container.PaintingPressContainerMenu;
import com.ncg233.tutorialmod.bibliocraftest.helper.BiblioEnums;
import com.ncg233.tutorialmod.bibliocraftest.helper.PaintingUtil;

public class PaintingPressScreen extends AbstractContainerScreen<PaintingPressContainerMenu>{
    //这个类里面里面所有的System.out.println()都没在终端看见
    private static int guiWidth = 256;
    private static int guiHeight = 241;
    private PaintingPressBlockEntity blockEntity;

    public static PaintingVariant[] vanillaArtList= new PaintingVariant[30] ;
    public static String[] vanillaArtListName=new String[30] ;

    private BiblioEnums.EnumBiblioPaintings[] biblioArtList = BiblioEnums.EnumBiblioPaintings.values();

   private int selectedTypeArt=0;
    private String selectedArtTitle="blank";
    private int selectedVanillaArt = -1;
    private int selectedBiblioArt = -1;
    private int selectedCustomArt = -1;

    private int tab=0;

    private int showCount = 0;
    private int artCount = 0;
    private boolean drawCheck = false;

    private Button applyPainting;
    private GuiButtonNextPage pageNext;
    private GuiButtonNextPage pagePrev;

    private String[] customArtNames=null;
    private int[] customArtHeights = null;
    private int[] customArtWidths = null;
    private ResourceLocation[] customArtResources = null;
    private int pagesTotal = 1;
    private int pagesCurrent = 0;

    public PaintingPressScreen(PaintingPressContainerMenu menu, Inventory inventoryIn, Component componentIn) {

        super(menu,inventoryIn,componentIn);
        System.out.println("gouzao---------------------------");
        this.imageWidth = guiWidth;
        this.imageHeight = guiHeight;
       blockEntity= menu.getEntity();

        this.tab = this.selectedTypeArt;

        this.customArtNames= PaintingUtil.customArtNames;
        if (this.customArtNames != null) {
            this.customArtResources = PaintingUtil.customArtResources;
            this.customArtHeights = PaintingUtil.customArtHeights;
            this.customArtWidths = PaintingUtil.customArtWidths;

            float baseNum = this.customArtNames.length / 32.0f;
            int roundDown = this.customArtNames.length / 32;
            if ((baseNum - roundDown) > 0.0f) {
                this.pagesTotal = roundDown + 1;
            } else if (roundDown == 0) {
                this.pagesTotal = 1;
            } else {
                this.pagesTotal = roundDown;
            }
        }
        updateArtSelection();
    }

    private void updateArtSelection() {
        switch (this.selectedTypeArt) {
            case 0: {
                for (int i = 0; i < biblioArtList.length; i++) {
                    if (this.selectedArtTitle.contentEquals(this.biblioArtList[i].title)) {
                        this.selectedBiblioArt = i;
                    }
                }
                break;
            }
            case 1: {

            }
            case 2: {
                if (customArtNames != null && customArtNames.length > 0) {
                    for (int i = 0; i < customArtNames.length; i++) {
                        if (this.selectedArtTitle.contentEquals(this.customArtNames[i])) {
                            this.selectedCustomArt = i;
                            this.pagesCurrent = i / 32;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(PoseStack poseStack, int mousex, int mousey, float partialTicks) {

        System.out.println("before render-------------------");
        super.renderBackground(poseStack);
        super.render(poseStack, mousex, mousey, partialTicks);

        this.renderTooltip(poseStack, mousex, mousey);
    }

    @Override
    protected void init() {
        super.init();
        System.out.println("init--------------------------------------------");
        this.addRenderableWidget(this.applyPainting=new Button(leftPos+52,topPos+137,60,20,Component.literal("gui.paintpress.transfer"),(button)->{}));
        this.addRenderableWidget(this.pagePrev=new GuiButtonNextPage(leftPos+9,topPos+134,false));
        this.addRenderableWidget(this.pageNext=new GuiButtonNextPage(leftPos+222,topPos+134,true));

        if (this.pagesTotal > 1 && this.tab == 2 && this.pagesTotal != this.pagesCurrent+1)
        {
           this.pageNext.active=true;
            this.pagePrev.visible=true;
        }
        else
        {
            this.pageNext.active = false;
            this.pageNext.visible = false;
        }
        if (this.pagesCurrent > 0)
        {
            this.pagePrev.active = true;
            this.pagePrev.visible = true;
        }
        else {
            this.pagePrev.active = false;
            this.pagePrev.visible = false;
        }
    }

    @Override
    protected void renderBg(PoseStack poseStack, float p_97788_, int mousex, int mousey) {
        RenderSystem.setShaderTexture(0,CommonProxy.PAINTINGPRESSGUI);
        blit(poseStack,leftPos,topPos,0,0,imageWidth,imageHeight);
        System.out.print("behindblit----------------------------------------------------------");
        if(this.tab==0){
            blit(poseStack,leftPos+14,topPos,0,244,75,12);
        } else if (this.tab==1) {
            blit(poseStack,leftPos+90,topPos,0,244,75,12);
        } else if (this.tab==2) {
            blit(poseStack,leftPos+166,topPos,0,244,75,12);
        }
        RenderSystem.setShaderTexture(0, CommonProxy.PAINTINGPRESSBUTTONS);

        poseStack.pushPose();
        poseStack.translate(leftPos,topPos,1);

        if (this.tab == 0) {

            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 8; i++) {
                    if (i + j * 8 < this.biblioArtList.length) {
                        if (mousex > (12 + i * 29) && mousex <= (40 + i * 29) && mousey > (19 + (j * 29))
                                && mousey < (47 + (j * 29)))
                        {
                            if (i + j * 8 == this.selectedBiblioArt) {
                                blit(poseStack, 12 + i * 29, 19 + (j * 29), 90, 0, 28, 28);
                                drawCheck = true;
                            } else {
                                blit(poseStack, 12 + i * 29, 19 + (j * 29), 30, 0, 28, 28);
                            }
                        } else if (i + j * 8 == this.selectedBiblioArt) {
                            blit(poseStack, 12 + i * 29, 19 + (j * 29), 60, 0, 28, 28);
                            drawCheck = true;

                        } else {
                            blit(poseStack, 12 + i * 29, 19 + (j * 29), 0, 0, 28, 28);
                        }
                        RenderSystem.setShaderTexture(0, biblioArtList[i + j * 8].paintingTextures[0][0]);

                        float scaler = 24.0f / 256.0f;
                        float antiScaler = 1.0f / scaler;
                        poseStack.scale(scaler, scaler, 1.0f);

                        blit(poseStack, 14 + i * 29,
                                21 + j * 29, 0, 0, 256, 256);
                        poseStack.scale(antiScaler, antiScaler, 1.0f);

                        if (drawCheck) {
                            RenderSystem.setShaderTexture(0, CommonProxy.PAINTINGPRESSBUTTONS);
                            blit(poseStack,32 + i * 29,18 + j * 29, 0, 30, 10, 8);
                            drawCheck = false;
                        }
                        poseStack.popPose();
                    }
                }
            }
            
            if (this.tab == 1) {
                this.artCount = 0;
                for (int j = 0; j < 4; j++)
                {
                    for (int i = 0; i < 8; i++)
                    {
                        if (this.artCount < this.vanillaArtList.length) {

                            RenderSystem.setShaderTexture(0,CommonProxy.PAINTINGPRESSBUTTONS);
                            if (mousex > (  12 + i * 29) && mousex <= (  40 + i * 29) && mousey > (  19 + j * 29)
                                    && mousey < (  47 + j * 29)) 
                            {
                                if (this.artCount == this.selectedVanillaArt) {
                                    this. blit( poseStack, 12 + i * 29,   19 + j * 29, 90, 0, 28, 28);
                                    drawCheck = true;
                                } else {
                                    this. blit(  poseStack,12 + i * 29,   19 + j * 29, 30, 0, 28, 28);
                                }
                            } else if (this.artCount == this.selectedVanillaArt) {
                                this. blit(poseStack,12 + i * 29,   19 + j * 29, 60, 0, 28, 28);
                                drawCheck = true;

                            } else {
                                this. blit(poseStack,12 + i * 29,   19 + j * 29, 0, 0, 28, 28);
                            }

                            drawVanillaPainting(poseStack,i * 29,   j * 29, this.artCount);

                            if (drawCheck) {
                                RenderSystem.setShaderTexture(0,CommonProxy.PAINTINGPRESSBUTTONS);
                                this.blit(poseStack,32 + i * 29,   18 + j * 29, 0, 30, 10, 8);
                                drawCheck = false;
                            }
                            this.artCount++;
                        }
                    }
                }
            }
            if (this.tab == 2) {
                if (this.customArtNames != null) {
                    for (int j = 0; j < 4; j++) {
                        for (int i = 0; i < 8; i++) {
                            if (i + j * 8 + (32 * (this.pagesCurrent)) < this.customArtNames.length) {
                                   RenderSystem.setShaderTexture(0,CommonProxy.PAINTINGPRESSBUTTONS);
                                this. blit(  poseStack,12 + i * 29,    19 + j * 29, 0, 0, 28, 28);

                                if (mousex > (  12 + i * 29) && mousex <= (  40 + i * 29) && mousey > (   19 + j * 29)
                                        && mousey < (   47 + j * 29)) 
                                {
                                    if (i + j * 8 + (32 * (this.pagesCurrent)) == this.selectedCustomArt) {
                                        this. blit( poseStack, 12 + i * 29,    19 + j * 29, 90, 0, 28, 28);
                                        drawCheck = true;
                                    } else {
                                        this. blit(poseStack,12 + i * 29,    19 + j * 29, 30, 0, 28, 28);
                                    }
                                } else if (i + j * 8 + (32 * (this.pagesCurrent)) == this.selectedCustomArt) {
                                    this. blit( poseStack,12 + i * 29,    19 + j * 29, 60, 0, 28, 28);
                                    drawCheck = true;

                                } else {
                                    this. blit( poseStack ,12 + i * 29,    19 + j * 29, 0, 0, 28, 28);
                                }

                                RenderSystem.setShaderTexture(0,ResourceLocation.fromNamespaceAndPath("bibliocraft", "textures/custompaintings/"
                                                + customArtNames[i + j * 8 + (32 * (this.pagesCurrent))]));

                                float scaler = 24.0f / 256.0f;
                                float antiScaler = 1.0f / scaler;
                                poseStack.scale(scaler, scaler, 1.0f);
                                
                                this. blit(poseStack, 14 + i * 29,
                                         21 + j * 29, 0, 0, 256, 256);

                                poseStack.scale(antiScaler, antiScaler, antiScaler);

                                if (drawCheck) {
                                       RenderSystem.setShaderTexture(0, CommonProxy.PAINTINGPRESSBUTTONS);
                                    this. blit( poseStack, 32 + i * 29,18 + j * 29, 0, 30, 10, 8);
                                    drawCheck = false;
                                }
                                poseStack.popPose();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        sentPacket(false);
    }

    private void sentPacket(boolean applyToCanvas) {

    }

    private void drawVanillaPainting(PoseStack poseStack, int x, int y, int paintingNum) {
        
        float scale = 24.0f / (float)this.vanillaArtList[paintingNum].getWidth();
        if (this.vanillaArtList[paintingNum].getWidth() < this.vanillaArtList[paintingNum].getHeight()) {
            scale = 24.0f / this.vanillaArtList[paintingNum].getHeight();
        }
      RenderSystem.setShaderTexture(0,
              ResourceLocation.parse("textures/painting/"+vanillaArtListName[paintingNum]+"/.png"));

        double invertedScale = 1.0 / scale;

        poseStack.scale(scale, scale, scale);
        
        blit(poseStack,x+14 ,
                y+22, 0,
                0, this.vanillaArtList[paintingNum].getWidth(),
                this.vanillaArtList[paintingNum].getHeight());
        poseStack.scale(1.0f / scale, 1.0f / scale, 1.0f / scale);
    }

    @Override
    public boolean mouseClicked(double mousex, double mousey, int click) {
            super.mouseClicked(mousex, mousey, click);
        
        if (mousey > topPos&& mousey <= topPos) {
            if (mousex > leftPos + 14 && mousex < leftPos+ 89) 
            {
                
                if (this.tab != 0) {
                    this.setTab(0);
                    this.init();
                }
            }
            if (mousex > leftPos + 90 && mousex < leftPos + 165) 
            {
                
                if (this.tab != 1) {
                    this.setTab(1);
                    this.init();
                }
            }
            if (mousex > leftPos + 166&& mousex < leftPos + 241) 
            {
                
                if (this.tab != 2) {
                    this.setTab(2);
                   
                }
            }

            if (this.tab == 0) {

                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 8; i++) {
                        if (i + j * 8 < this.biblioArtList.length) {
                            if (mousex > (leftPos+ 12 + i * 29) && mousex <= (leftPos+ 40 + i * 29) && mousey > (topPos+ 19 + j * 29)
                                    && mousey < (topPos+ 47 + j * 29)) 
                            {
                                if (click == 1) {
                                    this.selectedBiblioArt = -1;
                                    this.selectedArtTitle = "blank";
                                } else {
                                    this.selectedBiblioArt = i + j * 8;
                                    this.selectedVanillaArt = -1;
                                    this.selectedCustomArt = -1;
                                    this.selectedTypeArt = 0;
                                    this.selectedArtTitle = this.biblioArtList[i + j * 8].title;
                                }
                            }
                        }
                    }
                }
            }

            if (this.tab == 1) {
                this.artCount = 0;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 8; i++) {
                        if (this.artCount < this.vanillaArtList.length) {
                            if (mousex > (leftPos+ 12 + i * 29) && mousex <= (leftPos+ 40 + i * 29) && mousey > (topPos+ 19 + j * 29)
                                    && mousey < (topPos+ 47 + j * 29)) 
                            {
                                if (click == 1) {
                                    this.selectedVanillaArt = -1;
                                    this.selectedArtTitle = "blank";
                                } else {
                                    this.selectedVanillaArt = this.artCount;
                                    this.selectedBiblioArt = -1;
                                    this.selectedCustomArt = -1;
                                    this.selectedTypeArt = 1;
                                    this.selectedArtTitle = this.vanillaArtListName[this.artCount];
                                }
                            }
                            this.artCount++;
                        }
                    }
                }
            }

            if (this.tab == 2) {
                
                if (this.customArtNames != null && this.customArtNames.length> 0) {
                    for (int j = 0; j < 4; j++) {
                        for (int i = 0; i < 8; i++) {
                            if (i + j * 8 + (32 * (this.pagesCurrent)) < this.customArtNames.length) {
                                if (mousex > (leftPos+ 12 + i * 29) && mousex <= (leftPos+ 40 + i * 29) && mousey > (topPos+ 19 + j * 29)
                                        && mousey < (topPos+ 47 + j * 29)) 
                                {
                                    if (click == 1) {
                                        this.selectedCustomArt = -1;
                                        this.selectedArtTitle = "blank";
                                    } else {
                                        this.selectedCustomArt = i + j * 8 + (32 * (this.pagesCurrent));
                                        this.selectedBiblioArt = -1;
                                        this.selectedTypeArt = 2;
                                        this.selectedArtTitle = this.customArtNames[this.selectedCustomArt];
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    private void setTab(int tabe)
    {
        this.tab = tabe;
        this.init();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (keyCode == 1) {
            this.onClose();
        }
        if (keyCode == 15) 
        {
            if (this.tab < 2) {
                this.tab++;
            } else {
                this.tab = 0;
            }
        }
        return  false;
    }
}

