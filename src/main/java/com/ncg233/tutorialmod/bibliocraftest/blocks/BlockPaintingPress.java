package com.ncg233.tutorialmod.bibliocraftest.blocks;

import com.ncg233.tutorialmod.bibliocraftest.blockentities.PaintingPressBlockEntity;
import com.ncg233.tutorialmod.bibliocraftest.container.PaintingPressContainerMenu;
import com.ncg233.tutorialmod.bibliocraftest.items.ItemPaintingCanvas;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlockPaintingPress extends Block implements EntityBlock//NetworkHooks.openScreen在86行
{
    public static final String name = "PaintingPress";
    public BlockPaintingPress(Properties properties) {
        super(properties);

    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PaintingPressBlockEntity(blockPos,blockState);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!level.isClientSide && blockEntity instanceof PaintingPressBlockEntity paintPress) {
            ItemStack playerHand = player.getItemInHand(hand);

            if (!playerHand.isEmpty() && playerHand.getItem() instanceof ItemPaintingCanvas) {
                int canvasAddReturn = paintPress.addCanvas(playerHand);
                if (canvasAddReturn == 0) {
                    player.setItemInHand(hand, ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                } else if (canvasAddReturn > 0) {
                    playerHand.setCount(canvasAddReturn);
                    player.setItemInHand(hand, playerHand);
                    return InteractionResult.SUCCESS;
                }
            }

            if (player.isShiftKeyDown()) {
                if (!paintPress.getStackInSlot(0).isEmpty()) {

                    return InteractionResult.SUCCESS;
                }
            }

            Direction angle = paintPress.getAngle();
            if (isFrontHandleSpot(angle, hit.getDirection(),
                    (float)hit.getLocation().x,
                    (float)hit.getLocation().y,
                    (float)hit.getLocation().z)) {
                if (!paintPress.getStackInSlot(0).isEmpty()) {
                    paintPress.setCycle(true);
                    return InteractionResult.SUCCESS;
                }
            }

            if (player instanceof ServerPlayer serverPlayer) {//两个终端都能看见
                System.out.println("before  NetworkHooks.openScreen===========");
                NetworkHooks.openScreen(serverPlayer,
                        new MenuProvider() {
                            @Override
                            public Component getDisplayName() {
                                return Component.literal("container.bibliocraft.painting_press");
                            }

                            @Override
                            public @Nullable AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {
                                return new PaintingPressContainerMenu(windowId, pos,playerInv,player);
                            }
                        },
                        pos
                );
            }
            System.out.println("behind NetworkHooks.openScreen-----------");
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public boolean isFrontHandleSpot(Direction angle, Direction face, float hitX, float hitY, float hitZ) {
        switch (angle) {
            case SOUTH:
                return (face == Direction.WEST && hitY > 0.75) ||
                        (face == Direction.UP && hitX < 0.16f);
            case WEST:
                return (face == Direction.NORTH && hitY > 0.75) ||
                        (face == Direction.UP && hitZ < 0.16f);
            case NORTH:
                return (face == Direction.EAST && hitY > 0.75) ||
                        (face == Direction.UP && hitX > 0.84f);
            case EAST:
                return (face == Direction.SOUTH && hitY > 0.75) ||
                        (face == Direction.UP && hitZ > 0.84f);
            default:
                return false;
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public List<String> getModelParts(PaintingPressBlockEntity tile) {
        List<String> modelParts = new ArrayList<>();
        modelParts.add("base");
        if (!tile.getStackInSlot(0).isEmpty()) {
            modelParts.add("canvas");
        }
        return modelParts;
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
       if(level.isClientSide) {
           return null;
       }
       return (tempLevel,pos,tempBlockState,tempType)->{
           if(tempType instanceof PaintingPressBlockEntity tile){
               tile.tick();
           }
       };
    }

}

