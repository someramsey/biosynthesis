package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.registrate.ParticleTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VesselBlock extends BaseEntityBlock {
    public static DirectionProperty FacingProperty = BlockStateProperties.FACING;
    public static IntegerProperty AgeProperty = IntegerProperty.create("age", 0, 5);

    public VesselBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).noOcclusion().strength(-1.0F, 3600000.0F).noLootTable());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FacingProperty);
        pBuilder.add(AgeProperty);
    }

    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        double offset = 0.5d;
        double shift = 0.1d;

        double d0 = (double) pPos.getX() + offset - pRandom.nextDouble() * shift;
        double d1 = (double) pPos.getY() + offset - pRandom.nextDouble() * shift;
        double d2 = (double) pPos.getZ() + offset - pRandom.nextDouble() * shift;

        pLevel.addParticle(ParticleTypeRegistry.bloodParticleType.get(), d0, d1, d2, 0, 0, 0);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        //TODO: Update the shape according to model
        return Shapes.box(0.25, 0, 0.25, 0.75, 0.5, 0.75);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new VesselBlockEntity(pPos, pState);
    }
}
