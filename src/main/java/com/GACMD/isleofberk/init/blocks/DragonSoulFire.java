package com.GACMD.isleofberk.init.blocks;

import net.minecraft.world.level.block.FireBlock;

public class DragonSoulFire extends FireBlock {

	public DragonSoulFire(Properties blockProperties) {
		super(blockProperties);
	}

//	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
//		return canSurviveOnBlock(pState);
//	}
//
//	public static boolean canSurviveOnBlock(BlockState blockState) {
//		return !blockState.is(Blocks.AIR); // We probably dont want air to burn...
//	}
//
//	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
//		return getState(pContext.getLevel(), pContext.getClickedPos());
//	}
//
//	public static BlockState getState(BlockGetter pReader, BlockPos pPos) {
//		BlockPos blockpos = pPos.below();
//		BlockState blockstate = pReader.getBlockState(blockpos);
//		return DragonSoulFire.canSurviveOnBlock(blockstate) ? ((DragonSoulFire) BlockInit.DRAGON_SOUL_FIRE.get()).getStateForPlacement(pReader, pPos) : Blocks.AIR.defaultBlockState();
//	}
//
//	@Override
//	protected boolean canBurn(BlockState pState) {
//		return true;
//	}
}
