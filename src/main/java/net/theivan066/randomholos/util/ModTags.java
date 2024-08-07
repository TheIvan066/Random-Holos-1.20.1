package net.theivan066.randomholos.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.theivan066.randomholos.RandomHolos;

public class ModTags {
    public static class Items {

        public static final TagKey<Item> HUMIDIFIER_USABLE = tag("humidifier_usable");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(RandomHolos.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Blocks {


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(RandomHolos.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> IS_KAKURIYO = tag("is_kakuriyo");
        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(RandomHolos.MOD_ID, name));
        }
    }
}
