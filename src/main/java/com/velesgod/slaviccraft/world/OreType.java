package com.velesgod.slaviccraft.world;

import com.velesgod.slaviccraft.core.init.BlockInit;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;

public enum OreType {

    AMBER_ORE_TYPE(Lazy.of(BlockInit.AMBER_ORE), 4, 4, 64);

    private final Lazy<Block> block;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;

    OreType(Lazy<Block> block, int maxVeinSize, int minHeight, int maxHeight) {
        this.block = block;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public Lazy<Block> getBlock() {
        return block;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public static OreType get(Block block) {
        for (OreType ore : values()) {
            if(block == ore.block) {
                return ore;
            }
        }
        return null;
    }
}