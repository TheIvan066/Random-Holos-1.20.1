package net.theivan066.randomholos;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.theivan066.randomholos.block.ModBlocks;
import net.theivan066.randomholos.block.entity.ModBlockEntities;
import net.theivan066.randomholos.effect.ModEffects;
import net.theivan066.randomholos.enchantment.ModEnchantments;
import net.theivan066.randomholos.entity.ModEntities;
import net.theivan066.randomholos.entity.client.*;
import net.theivan066.randomholos.entity.client.projectile.BulletProjectileRenderer;
import net.theivan066.randomholos.entity.client.projectile.GuesserPinProjectileRenderer;
import net.theivan066.randomholos.entity.client.projectile.MikometArrowRenderer;
import net.theivan066.randomholos.fluid.ModFluidTypes;
import net.theivan066.randomholos.fluid.ModFluids;
import net.theivan066.randomholos.item.ModCreativeModeTabs;
import net.theivan066.randomholos.item.ModItemProperties;
import net.theivan066.randomholos.item.ModItems;
import net.theivan066.randomholos.loot.ModLootModifiers;
import net.theivan066.randomholos.particle.ModParticles;
import net.theivan066.randomholos.recipe.ModRecipes;
import net.theivan066.randomholos.screen.ManufacturingTableScreen;
import net.theivan066.randomholos.screen.ModMenuTypes;
import net.theivan066.randomholos.sound.ModSounds;
import net.theivan066.randomholos.util.ModWoodTypes;
import net.theivan066.randomholos.worldgen.tree.trunk_placer.ModTrunkPlacerTypes;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RandomHolos.MOD_ID)
public class RandomHolos {
    public static final String MOD_ID = "randomholos";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RandomHolos() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);

        ModEnchantments.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEffects.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModParticles.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        ModTrunkPlacerTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.MAPLE_SAPLING.getId(), ModBlocks.POTTED_MAPLE_SAPLING);

        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.CHIVES.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModItems.SCALLION.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.MAPLE_LEAVES.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.MAPLE_SAPLING.get(), 0.3f);
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                Sheets.addWoodType(ModWoodTypes.MAPLE);

                ModItemProperties.addCustomItemProperties();

                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_ELITE_LAVA.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_ELITE_LAVA.get(), RenderType.translucent());

                EntityRenderers.register(ModEntities.SORA.get(), SoraRenderer::new);
                EntityRenderers.register(ModEntities.NUNNUN.get(), NunnunRenderer::new);
                EntityRenderers.register(ModEntities.ROBOCO.get(), RobocoRenderer::new);
                EntityRenderers.register(ModEntities.SUISEI.get(), SuiseiRenderer::new);
                EntityRenderers.register(ModEntities.MIKO.get(), MikoRenderer::new);
                EntityRenderers.register(ModEntities.MIKOP.get(), MikopRenderer::new);
                EntityRenderers.register(ModEntities.GLASS_HEELS_PROJECTILE.get(), ThrownItemRenderer::new);
                EntityRenderers.register(ModEntities.GUESSER_PIN_PROJECTILE.get(), GuesserPinProjectileRenderer::new);
                EntityRenderers.register(ModEntities.MIKOMET_ARROW.get(), MikometArrowRenderer::new);
                EntityRenderers.register(ModEntities.BULLET_PROJECTILE.get(), BulletProjectileRenderer::new);

                MenuScreens.register(ModMenuTypes.MANUFACTURING_TABLE_MENU.get(), ManufacturingTableScreen::new);
            });
        }
    }
}
