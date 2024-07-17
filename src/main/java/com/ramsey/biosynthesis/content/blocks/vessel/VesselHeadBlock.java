package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.content.blocks.branch.BranchStemBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.registrate.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class VesselHeadBlock extends VesselBlock {
    public VesselHeadBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(
            stateDefinition.any().setValue(FacingProperty, Direction.UP)
        );
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        Orientation orientation = Orientation.Horizontal[pRandom.nextInt(4)];
        SpreadTask task = new SpreadTask(pLevel, pRandom, pPos, orientation);

        boolean spreadDone = false;
        while (!spreadDone) {
            spreadDone = task.spread();
        }
    }

    private static class SpreadTask {
        private final ServerLevel level;
        private final RandomSource random;

        private BlockPos pos;
        private Orientation orientation;

        public SpreadTask(ServerLevel level, RandomSource random, BlockPos pos, Orientation orientation) {
            this.level = level;
            this.random = random;
            this.orientation = orientation;
            this.pos = pos;
        }

        private Block getBlock(BlockPos pos) {
            return level.getBlockState(pos).getBlock();
        }

        private boolean canSupport(BlockPos pos) {
            return Block.isShapeFullBlock(level.getBlockState(pos).getCollisionShape(level, pos));
        }

        private boolean spread() {
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();

            if (block == BlockRegistry.vesselBlock.get()) {
                BlockPos below = pos.below();

                if (!canSupport(below.north())) {
                    return true;
                }

                int age = state.getValue(AgeProperty);

                if (age < VesselBlock.MaxAge) {
                    VesselBlock.grow(level, state, pos, age + 1);
                    return true;
                }

                if (getBlock(pos.north()) != Blocks.AIR) {

                }

                return false;
            }


            if (block == Blocks.AIR) {
                BlockState newState = BlockRegistry.stemBlock.get().defaultBlockState()
                    .setValue(BranchStemBlock.OrientationProperty, orientation)
                    .setValue(BranchStemBlock.RootedProperty, true);
                System.out.println(newState.getValue(BranchStemBlock.AgeProperty));

                level.setBlock(newPos, newState, 2);
            } else if (block == BlockRegistry.stemBlock.get()) {
                int age = state.getValue(BranchStemBlock.AgeProperty);

                if (age == 4) {
                    int orientationIndex = random.nextInt(4);
                    Orientation newOrientation = Orientation.Horizontal[orientationIndex];

                    if (newOrientation == orientation) {
                        newOrientation = Orientation.Horizontal[(orientationIndex + 1) % 4];
                    }

                    pos = newPos;
                    orientation = newOrientation;

                    return false;
                }

                level.setBlock(newPos, state.setValue(BranchStemBlock.AgeProperty, age + 1), 2);
            }

            return true;
        }
    }
}
