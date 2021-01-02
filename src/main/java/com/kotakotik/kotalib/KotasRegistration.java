package com.kotakotik.kotalib;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Kota's class with many methods to make registration easier
 */
public class KotasRegistration {
    public static <T extends Block> RegistryObject<T> registerBlock(String id, Supplier<T> block, Supplier<BlockItem> blockItem, DeferredRegister<Item> deferredRegister, DeferredRegister<Block> deferredRegisterBlock) {
        RegistryObject<T> ret = registerBlockNoItem(id, block, deferredRegisterBlock);
        deferredRegister.register(id, blockItem);
        return ret;
    }

    public static <T extends Block> RegistryObject<T> registerBlockNoItem(String id, Supplier<T> block, DeferredRegister<Block> deferredRegister) {
        return deferredRegister.register(id, block);
    }

    public static class BlockRegister {
        public final DeferredRegister<Item> itemDeferredRegister;
        public final DeferredRegister<Block> blockDeferredRegister;

        public BlockRegister(DeferredRegister<Item> itemDeferredRegister, DeferredRegister<Block> blockDeferredRegister) {
            this.itemDeferredRegister = itemDeferredRegister;
            this.blockDeferredRegister = blockDeferredRegister;
        }

        public <T extends Block> RegistryObject<T> registerBlock(String id, Supplier<T> block, Supplier<BlockItem> blockItem) {
            return KotasRegistration.registerBlock(id, block, blockItem, itemDeferredRegister, blockDeferredRegister);
        }

        public <T extends Block> RegistryObject<T> registerBlock(String id, Supplier<T> block) {
            return registerBlock(id, block, () -> new BlockItem(block.get(), new Item.Properties()));
        }
    }
}
