package com.ncg233.tutorialmod.bibliocraftest.blockentities;

import com.ncg233.tutorialmod.bibliocraftest.register.ModBlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import com.ncg233.tutorialmod.bibliocraftest.items.ItemPaintingCanvas;

public class PaintingPressBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler inventory = new ItemStackHandler(1) {

    };
    private Direction angle = Direction.NORTH;
    private boolean cycleLid = false;
    private boolean setPainting = false;
    private int cycleCounter = 0;
    private float lidAngle = 0.0f;
    private int selectedPaintingType = 0;
    private String selectedPaintingTitle = "blank";
    private boolean isRetexturable = false;

     public PaintingPressBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public PaintingPressBlockEntity( BlockPos pos, BlockState state) {
        this(ModBlockEntityInit.PAINTING_PRESS_BLOCK_ENTITY.get(), pos, state);
    }

    public int addCanvas(ItemStack canvasStack) {
        if (canvasStack.getItem() instanceof ItemPaintingCanvas) {
            ItemStack currentSlot = inventory.getStackInSlot(0);
            if (currentSlot.isEmpty()) {
                if (canvasStack.getCount() > 1) {
                    ItemStack newCanvas = canvasStack.copy();
                    newCanvas.setCount(1);
                    inventory.setStackInSlot(0, newCanvas);
                    canvasStack.setCount(canvasStack.getCount() - 1);
                    setChanged();
                    return canvasStack.getCount();
                } else {
                    inventory.setStackInSlot(0, canvasStack);
                    setChanged();
                    return 0;
                }
            }
        }
        return canvasStack.getCount();
    }

    public void setSelectedPainting(int type, String title) {
        this.selectedPaintingType = type;
        this.selectedPaintingTitle = title;
        setChanged();
    }

    public void setCycle(boolean cycle) {
        if (cycle && (this.cycleLid || this.setPainting)) return;
        this.cycleLid = cycle;
        this.setPainting = cycle;
        setChanged();
    }

    public void setCanvasPainting() {
        ItemStack canvas = inventory.getStackInSlot(0);
        if (!canvas.isEmpty()) {
            CompoundTag tags = canvas.getTag();
            if (tags == null) tags = new CompoundTag();
            tags.putInt("paintingType", this.selectedPaintingType);
            tags.putString("paintingTitle", this.selectedPaintingTitle);
            canvas.setTag(tags);
            inventory.setStackInSlot(0, canvas);
            setChanged();
        }
    }

    public int getPaintingType() {
        ItemStack canvas = inventory.getStackInSlot(0);
        if (!canvas.isEmpty() && canvas.hasTag()) {
            return canvas.getTag().getInt("paintingType");
        }
        return 0;
    }

    public String getPaintingTitle() {
        ItemStack canvas = inventory.getStackInSlot(0);
        if (!canvas.isEmpty() && canvas.hasTag()) {
            return canvas.getTag().getString("paintingTitle");
        }
        return "blank";
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", inventory.serializeNBT()); 
        tag.putString("angle", angle.getSerializedName()); 
        tag.putBoolean("cycleLid", cycleLid);
        tag.putBoolean("setPainting", setPainting);
        tag.putInt("cycleCounter", cycleCounter);
        tag.putFloat("lidAngle", lidAngle);
        tag.putInt("selectedPaintingType", selectedPaintingType);
        tag.putString("selectedPaintingTitle", selectedPaintingTitle);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("inventory")); 
        angle = Direction.byName(tag.getString("angle")); 
        cycleLid = tag.getBoolean("cycleLid");
        setPainting = tag.getBoolean("setPainting");
        cycleCounter = tag.getInt("cycleCounter");
        lidAngle = tag.getFloat("lidAngle");
        selectedPaintingType = tag.getInt("selectedPaintingType");
        selectedPaintingTitle = tag.getString("selectedPaintingTitle");
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        if (cycleLid) {
            if (cycleCounter > 20) {
                lidAngle = 0.0f;
                cycleCounter = 0;
                cycleLid = false;
                setChanged();
            } else {
                if (cycleCounter > 10) {
                    lidAngle -= 2.5f;
                    if (setPainting) {
                        setPainting = false;
                        setCanvasPainting();
                    }
                } else {
                    if (lidAngle <= 22.5f) {
                        lidAngle += 2.5f;
                    } else {
                        lidAngle = 25.0f;
                    }
                }
                cycleCounter++;
                setChanged();
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return  Component.literal("Painting Press");
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {

        return null; 
    }

    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    public Direction getAngle() {
        return angle;
    }

    public void setAngle(Direction angle) {
        this.angle = angle;
        setChanged();
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return stack.getItem() instanceof ItemPaintingCanvas;
    }
}
