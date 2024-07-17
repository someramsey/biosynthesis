package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.registrate.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceSpreader;
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
        int age = pState.getValue(VesselBlock.AgeProperty);

        if (age < 3) {
            pLevel.setBlock(pPos, pState.setValue(VesselBlock.AgeProperty, age + 1), 2);
            pLevel.sendParticles(ParticleTypes.POOF, pPos.getX() + 0.5d, pPos.getY() + 0.5d, pPos.getZ() + 0.5d, 20, 0.3d, 0.3d, 0.3d, 0);
            return;
        }

        spread(pLevel, pPos, pRandom);
    }

    private void spread(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        Direction direction = getSpreadDirection(pRandom);
        BlockState state = pLevel.getBlockState(pPos.relative(direction));
        Block block = state.getBlock();

        if (block == Blocks.AIR) {
            pLevel.setBlock(
                pPos.relative(getSpreadDirection(pRandom)),
                BlockRegistry.branchBlock.get().defaultBlockState()
                    .setValue(BranchBlock.OrientationProperty, Orientation.fromDirection(direction)),
                2);

            return;
        }

        //TODO: check if there is a vessel at position, grow if there is

    }

    private static Direction getSpreadDirection(@NotNull RandomSource pRandom) {
        return switch (pRandom.nextInt(3)) {
            default -> Direction.NORTH;
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.WEST;
            case 3 -> Direction.EAST;
        };
    }


}
