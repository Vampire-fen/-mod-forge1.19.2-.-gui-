package com.ncg233.tutorialmod.bibliocraftest.items;

import com.ncg233.tutorialmod.bibliocraftest.helper.BiblioCreativeTab;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPaintingCanvas extends Item
{
    private int dynamicMaxStackSize = 64;
    public static final String name = "PaintingCanvas";

    public ItemPaintingCanvas()
    {
        super(new Properties().tab(BiblioCreativeTab.BIBLIO_ITEM_GROUP));

    }
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag flag){

        CompoundTag tags = itemStack.getTag();
        if (tags != null)
        {
            String name = tags.getString("paintingTitle");
            if (name.length() > 0)
            {
                if (name.contains(".png"))
                {
                    name = name.replace(".png", "");
                }
                componentList.add(Component.literal(name));
            }
        }
        super.appendHoverText(itemStack, level, componentList, flag);
    }

  @Override
  public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

      CompoundTag tags = stack.getTag();
      if (tags == null) {
          tags = new CompoundTag();
          tags.putBoolean("blank", true);
          stack.setTag(tags);
      }

      if (tags.getBoolean("blank")) {
          if (this.getMaxStackSize() != 64) {
              dynamicMaxStackSize =64;
          }
      } else {
          if (this.getMaxStackSize() != 1) {
              dynamicMaxStackSize=1;
          }
      }
  }
    @Override
    public int getMaxStackSize(ItemStack stack) {
        return dynamicMaxStackSize;
    }

}

