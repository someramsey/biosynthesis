package com.ramsey.biosynthesis.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface SpreadingBlock {
    void spread(ServerLevel pLevel, BlockState pState, BlockPos pPos, RandomSource pRandom, SpreadTask pSpreadTask);

    static boolean canPropagate(Block pBlock) {
        return pBlock instanceof SpreadingBlock;
    }

    class SpreadTask {
        private final ServerLevel level;
        private final RandomSource random;

        private BlockPos pos;
        public boolean spreading;

        public SpreadTask(ServerLevel level, RandomSource random, BlockPos pos) {
            this.level = level;
            this.random = random;
            this.pos = pos;
            this.spreading = true;
        }

        private int propagationAttempts = 0; //TODO: remove fcked up check

        public void spread() {
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();

            if (block instanceof SpreadingBlock spreadingBlock) {
                spreadingBlock.spread(level, state, pos, random, this);

                //TODO: remove fcked up check
                if (propagationAttempts++ > 1000) {
                    spreading = false;

                    System.err.println("YOU FCKED UP AT " + spreadingBlock);
                }

                return;
            }

            this.spreading = false;
        }

        public void propagate(BlockPos pPos) {
            pos = pPos;
        }

        public void consume() {
            spreading = false;
        }
    }
}