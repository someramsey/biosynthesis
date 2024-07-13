package com.ramsey.biosynthesis.content.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public class BranchBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty ConnectedRightProperty = BooleanProperty.create("right");
    public static final BooleanProperty ConnectedLeftProperty = BooleanProperty.create("left");
    

    public BranchBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);

        pBuilder.add(ConnectedRightProperty);
        pBuilder.add(ConnectedLeftProperty);
    }
}
