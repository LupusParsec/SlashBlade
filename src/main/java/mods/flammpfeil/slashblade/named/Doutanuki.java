package mods.flammpfeil.slashblade.named;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by Furia on 14/11/08.
 */
public class Doutanuki {
    public static final String name = "flammpfeil.slashblade.named.sabigatana";
    public static final String namedou = "flammpfeil.slashblade.named.doutanuki";

    public static float spawnRate = 0.05f;
    public static float isBrokenRate = 0.7f;
    public static float noSheathRate = 0.9f;
    public static float dropRate = 0.2f;

    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        {
            String name = Doutanuki.name;
            ItemStack customblade = new ItemStack(SlashBlade.bladeNamed,1,0);
            NBTTagCompound tag = new NBTTagCompound();
            customblade.setTagCompound(tag);

            ItemSlashBladeNamed.CurrentItemName.set(tag, name);
            ItemSlashBladeNamed.CustomMaxDamage.set(tag, 50);
            ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.IRON.getDamageVsEntity());
            ItemSlashBlade.TextureName.set(tag, "named/muramasa/sabigatana");
            ItemSlashBlade.ModelName.set(tag, "named/muramasa/muramasa");
            ItemSlashBlade.SpecialAttackType.set(tag, 1);
            ItemSlashBlade.StandbyRenderType.set(tag, 2);
            ItemSlashBlade.IsSealed.set(tag,true);
            //ItemSlashBladeNamed.IsDefaultBewitched.set(tag,true);

            GameRegistry.registerCustomItemStack(name, customblade);
            ItemSlashBladeNamed.NamedBlades.add(SlashBlade.modid + ":" + name);

            {
                ItemStack cblade = customblade.copy();
                tag = ItemSlashBlade.getItemTagCompound(cblade);
                ItemSlashBlade.RepairCount.set(tag,5);
                ItemSlashBlade.ProudSoul.set(tag,1000);
                ItemSlashBlade.KillCount.set(tag,100);
                GameRegistry.registerCustomItemStack(name + ".doureqired", cblade);
                ItemSlashBladeNamed.NamedBlades.add(SlashBlade.modid + ":" + name + ".doureqired");
            }

            customblade = customblade.copy();
            tag = ItemSlashBlade.getItemTagCompound(customblade);
            ItemSlashBlade.IsNoScabbard.set(tag,true);
            GameRegistry.registerCustomItemStack(name + ".noscabbard", customblade);
            ItemSlashBladeNamed.NamedBlades.add(SlashBlade.modid + ":" + name + ".noscabbard");

            customblade = customblade.copy();
            tag = ItemSlashBlade.getItemTagCompound(customblade);
            ItemSlashBlade.IsBroken.set(tag,true);
            ItemSlashBlade.RepairCount.set(tag,1);
            GameRegistry.registerCustomItemStack(name + ".broken", customblade);
            ItemSlashBladeNamed.NamedBlades.add(SlashBlade.modid + ":" + name + ".broken");
        }

        {
            String name = Doutanuki.namedou;
            ItemStack customblade = new ItemStack(SlashBlade.bladeNamed,1,0);
            NBTTagCompound tag = new NBTTagCompound();
            customblade.setTagCompound(tag);

            ItemSlashBladeNamed.CurrentItemName.set(tag, name);
            ItemSlashBladeNamed.CustomMaxDamage.set(tag, 50);
            ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.IRON.getDamageVsEntity());
            ItemSlashBlade.TextureName.set(tag, "named/muramasa/doutanuki");
            ItemSlashBlade.ModelName.set(tag, "named/muramasa/muramasa");
            ItemSlashBlade.SpecialAttackType.set(tag, 5);
            ItemSlashBlade.StandbyRenderType.set(tag, 2);
            //ItemSlashBladeNamed.IsDefaultBewitched.set(tag,true);

            GameRegistry.registerCustomItemStack(name, customblade);
            ItemSlashBladeNamed.NamedBlades.add(SlashBlade.modid + ":" + name);
        }
    }

    @SubscribeEvent
    public void postinit(LoadEvent.PostInitEvent event){

        MinecraftForge.EVENT_BUS.register(this);

        try{
            SlashBlade.mainConfiguration.load();
            {
                Property prop;
                prop = SlashBlade.mainConfiguration.get("RustBlade","SpawnRate",(double)spawnRate);
                spawnRate = (float)prop.getDouble();
                spawnRate = Math.min(1.0f,Math.max(0.0f,spawnRate));
                prop.set(spawnRate);
            }
            {
                Property prop;
                prop = SlashBlade.mainConfiguration.get("RustBlade","IsBrokenRate",(double)isBrokenRate);
                isBrokenRate = (float)prop.getDouble();
                isBrokenRate = Math.min(1.0f,Math.max(0.0f,isBrokenRate));
                prop.set(isBrokenRate);
            }
            {
                Property prop;
                prop = SlashBlade.mainConfiguration.get("RustBlade","NoSheathRate",(double)noSheathRate,"0.0<rate<1");
                noSheathRate = (float)prop.getDouble();
                noSheathRate = Math.min(1.0f,Math.max(0.0f,noSheathRate));
                prop.set(noSheathRate);
            }
            {
                Property prop;
                prop = SlashBlade.mainConfiguration.get("RustBlade","DropRate",(double)dropRate,"0:nodrop , 0<droprate<1 , 2:forceDrop");
                dropRate = (float)prop.getDouble();
                dropRate = Math.max(0.0f,dropRate);
                prop.set(dropRate);
            }
        }
        finally
        {
            SlashBlade.mainConfiguration.save();
        }

        GameRegistry.addRecipe(new RecipeRepairBrokenBlade());
        RecipeSorter.register("flammpfeil.slashblade:rustsrepairbroken", RecipeRepairBrokenBlade.class, RecipeSorter.Category.SHAPED, "after:forge:shaped");

        GameRegistry.addRecipe(new RecipeSheath());
        RecipeSorter.register("flammpfeil.slashblade:rustsheath", RecipeSheath.class, RecipeSorter.Category.SHAPED, "after:forge:shaped");

        GameRegistry.addRecipe(new RecipeDoutanuki());
        RecipeSorter.register("flammpfeil.slashblade:doutanuki", RecipeDoutanuki.class, RecipeSorter.Category.SHAPED, "after:flammpfeil.slashblade:awake");

    }

    @SubscribeEvent
    public void specialSpawn(LivingSpawnEvent.SpecialSpawn event){
        if(event.entityLiving instanceof EntityZombie){
            if (spawnRate > event.entityLiving.getRNG().nextFloat())
            {

                ItemStack blade = SlashBlade.getCustomBlade(SlashBlade.modid , name);

                NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);


                if (noSheathRate > event.entityLiving.getRNG().nextFloat())
                    ItemSlashBlade.IsNoScabbard.set(tag,true);

                if(isBrokenRate > event.entityLiving.getRNG().nextFloat())
                    ItemSlashBlade.IsBroken.set(tag,true);



                ItemSlashBlade.KillCount.set(tag,event.entityLiving.getRNG().nextInt(200));

                if (0.1 > event.entityLiving.getRNG().nextFloat())
                    ItemSlashBlade.KillCount.add(tag,1000);


                event.entityLiving.setCurrentItemOrArmor(0, blade);
                ((EntityZombie)event.entityLiving).setEquipmentDropChance(0,dropRate);
            }
        }

    }

    public static class RecipeSheath extends ShapedOreRecipe {

        public RecipeSheath() {
            super(SlashBlade.getCustomBlade(SlashBlade.modid , name),
                    "  P",
                    " S ",
                    "B  ",
                    'P', GameRegistry.findItemStack(SlashBlade.modid, SlashBlade.ProudSoulStr, 1),
                    'S', setDamageWildCard(GameRegistry.findItemStack(SlashBlade.modid, "slashbladeWrapper", 1)),
                    'B', setDamageWildCard(getNoSeathBlade()));
        }

        static ItemStack setDamageWildCard(ItemStack stack){
            stack.setItemDamage(OreDictionary.WILDCARD_VALUE);
            return stack;
        }

        static ItemStack getNoSeathBlade(){
            ItemStack blade = SlashBlade.getCustomBlade(SlashBlade.modid , name);

            NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);

            ItemSlashBlade.IsNoScabbard.set(tag, true);

            return blade;
        }

        @Override
        public boolean matches(InventoryCrafting cInv, World par2World)
        {
            boolean result = super.matches(cInv,par2World);
            if(!result)
                return false;

            ItemStack sc = cInv.getStackInRowAndColumn(1, 1);
            if(!(sc != null && sc.getItem() == SlashBlade.wrapBlade && !SlashBlade.wrapBlade.hasWrapedItem(sc)))
                return false;

            ItemStack target = cInv.getStackInRowAndColumn(0, 2);
            if(target == null)
                return false;

            if(!(target.getItem() instanceof ItemSlashBlade))
                return false;

            if(!target.hasTagCompound())
                return false;

            NBTTagCompound tag = target.getTagCompound();
            if(!ItemSlashBlade.IsNoScabbard.get(tag))
                return false;

            if(!ItemSlashBladeNamed.CurrentItemName.get(tag).equals(name))
                return false;

            return true;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting cInv)
        {
            ItemStack target = cInv.getStackInRowAndColumn(0, 2);

            target = target.copy();

            NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(target);
            ItemSlashBlade.IsNoScabbard.set(tag, false);

            return target;
        }

    }

    public static class RecipeRepairBrokenBlade extends ShapedOreRecipe {

        public RecipeRepairBrokenBlade() {
            super(SlashBlade.getCustomBlade(SlashBlade.modid, name),
                    "  I",
                    " I ",
                    "B  ",
                    'I', GameRegistry.findItemStack(SlashBlade.modid, SlashBlade.IngotBladeSoulStr, 1),
                    'B', getBrokenBlade());
        }

        static ItemStack getBrokenBlade(){
            ItemStack blade = SlashBlade.getCustomBlade(SlashBlade.modid , name);

            NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);

            ItemSlashBlade.IsBroken.set(tag, true);
            ItemSlashBlade.RepairCount.set(tag, 1);


            return blade;
        }

        @Override
        public boolean matches(InventoryCrafting cInv, World par2World)
        {
            boolean result = super.matches(cInv,par2World);
            if(!result)
                return false;


            ItemStack target = cInv.getStackInRowAndColumn(0, 2);
            if(target == null)
                return false;

            if(!(target.getItem() instanceof ItemSlashBlade))
                return false;

            if(!target.hasTagCompound())
                return false;

            NBTTagCompound tag = target.getTagCompound();
            if(!ItemSlashBlade.IsBroken.get(tag))
                return false;

            if(ItemSlashBlade.RepairCount.get(tag) <= 0)
                return false;

            if(!ItemSlashBladeNamed.CurrentItemName.get(tag).equals(name))
                return false;

            return true;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting cInv)
        {
            ItemStack target = cInv.getStackInRowAndColumn(0, 2);

            target = target.copy();

            NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(target);
            ItemSlashBlade.IsBroken.set(tag, false);

            return target;
        }

    }

    public static class RecipeDoutanuki extends RecipeAwakeBlade {

        public RecipeDoutanuki() {
            super(SlashBlade.getCustomBlade(SlashBlade.modid , namedou),getBrokenBlade(),
                    "  S",
                    " B ",
                    "S  ",
                    'S', GameRegistry.findItemStack(SlashBlade.modid, SlashBlade.SphereBladeSoulStr, 1),
                    'B', getBrokenBlade());
        }

        static ItemStack getBrokenBlade(){
            ItemStack blade = SlashBlade.getCustomBlade(SlashBlade.modid, name);

            NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);

            ItemSlashBlade.RepairCount.set(tag, 5);
            ItemSlashBlade.ProudSoul.set(tag, 1000);
            ItemSlashBlade.KillCount.set(tag, 100);

            return blade;
        }

        @Override
        public boolean matches(InventoryCrafting cInv, World par2World)
        {
            boolean result = super.matches(cInv, par2World);
            if(!result)
                return false;


            ItemStack target = cInv.getStackInRowAndColumn(1, 1);
            if(target == null)
                return false;

            if(!(target.getItem() instanceof ItemSlashBlade))
                return false;

            if(!target.hasTagCompound())
                return false;

            NBTTagCompound tag = target.getTagCompound();
            if(ItemSlashBlade.IsBroken.get(tag))
                return false;

            if(ItemSlashBlade.IsNoScabbard.get(tag))
                return false;


            return true;
        }

    }
}