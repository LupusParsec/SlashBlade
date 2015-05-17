package mods.flammpfeil.slashblade.ability;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent;

/**
 * Created by Furia on 15/05/17.
 */
public class FireResistance {

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event){
        EntityLivingBase target = event.entityLiving;
        if(target == null) return;

        if(!(target instanceof EntityPlayer)) return;
        if(!((EntityPlayer) target).isUsingItem()) return;

        ItemStack stack = target.getHeldItem();
        if(stack == null) return;
        if(!(stack.getItem() instanceof ItemSlashBlade)) return;
        if(!stack.isItemEnchanted()) return;

        int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack);
        if(level <= 0) return;

        float speedfactor = 0.05f;
        speedfactor *= (float)level;

        if(target.isBurning()){
            target.moveFlying(target.moveStrafing,target.moveForward,0.25f + speedfactor);

            if(ItemSlashBlade.RequiredChargeTick < ((EntityPlayer) target).getItemInUseDuration()){
                ReflectionHelper.setPrivateValue(Entity.class, target, 0, "fire", "field_70151_c");
            }
        }

        target.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(),2,level-1,true));
    }
}
