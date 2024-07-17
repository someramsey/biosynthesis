package com.ramsey.biosynthesis.content.blocks.vessel;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
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

        System.out.println(age);
        
        if (age < 3) {
//            pLevel.setBlock(pPos, pState.setValue(VesselBlock.AgeProperty, age + 1), 2);

            return;
        }

    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        double offset = 0.3d;
        double offsetX = pRandom.nextDouble() + offset * 2 - offset;
        double offsetY = pRandom.nextDouble() + offset * 2 - offset;
        double offsetZ = pRandom.nextDouble() + offset * 2 - offset;
        pLevel.addParticle(ParticleTypes.POOF, pPos.getX() + offsetX, pPos.getY() + offsetY, pPos.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
    }
}
