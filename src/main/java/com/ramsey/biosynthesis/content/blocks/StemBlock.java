package com.ramsey.biosynthesis.content.blocks;

import com.ramsey.biosynthesis.data.providers.block.common.stem.StemBlockShapeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class StemBlock extends Block {
    public static final IntegerProperty AgeProperty = IntegerProperty.create("age", 0, 4);
    public static final DirectionProperty FacingProperty = BlockStateProperties.HORIZONTAL_FACING;

    private final StemBlockShapeProvider shapeProvider = new StemBlockShapeProvider();

    public StemBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);

        pBuilder.add(AgeProperty);
        pBuilder.add(FacingProperty);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return shapeProvider.buildShape(pState);
    }
}
