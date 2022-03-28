package com.velesgod.slaviccraft.blocks;

import com.velesgod.slaviccraft.blocks.tile.DrierTileEntity;
import com.velesgod.slaviccraft.contaniers.DrierBlockContainer;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class DrierBlock extends Block  implements EntityBlock{

	public static BooleanProperty LEVEL = BlockStateProperties.ATTACHED;
	
	
	public VoxelShape makeShape(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 1, 1), BooleanOp.OR);

		return shape;
	}
	
	
	public VoxelShape shapeMain = makeShape();
	
	public DrierBlock() {
		super( BlockBehaviour.Properties.of(Material.WOOD).dynamicShape().strength(0.3f,0.3f).noCollission());
		this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, false));
	}

	@Override
	   public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
	      return true;
	   }

	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {return RenderShape.MODEL;}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {return shapeMain;}
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
			InteractionHand p_60507_, BlockHitResult p_60508_) {
		if(!level.isClientSide()) {
			   if (!level.isClientSide && level.getBlockEntity(pos) instanceof final DrierTileEntity chest) {
		            final MenuProvider container = new SimpleMenuProvider(DrierBlockContainer.getServerContainer(chest, pos),
		            		new TextComponent(""));
		            NetworkHooks.openGui((ServerPlayer) player, container, pos);
		        }
		}
		return InteractionResult.SUCCESS;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState p_196243_1_, Level world, BlockPos pos, BlockState state,
			boolean p_196243_5_) {
//		TileEntity te = world.getBlockEntity(pos);
//		 if(te instanceof DrierTileEntity) {
//			 
//			 for(int i=0;i<8;i++)
//				 if(!((DrierTileEntity)te).inv().getStackInSlot(i).isEmpty())
//						world.addFreshEntity(new ItemEntity(world, 
//								pos.getX(), 
//								pos.getY() , 
//								pos.getZ(), 
//								 ((DrierTileEntity)te).inv().getStackInSlot(i)));
//			
//		 }
		super.onRemove(p_196243_1_, world, pos, state, p_196243_5_);
	}
	
	@Override
	   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
			builder.add(LEVEL);
	   }
	
	@Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> beType) {
        return level.isClientSide ? null
                : (level0, pos, state0, blockEntity) -> ((DrierTileEntity) blockEntity).tick();
    }

	@Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
		if(context.getLevel().getBlockState(context.getClickedPos().above()).getBlock() instanceof DrierBlock)		
			return this.defaultBlockState().setValue(LEVEL,true);
		else
			return this.defaultBlockState().setValue(LEVEL,false);
	}

	
	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
	if(world.getBlockState(pos.above()).isAir()) return false;
	return true; 
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block p_220069_4_,
			BlockPos p_220069_5_, boolean p_220069_6_) {

		if(!state.canSurvive(world,pos)) 
			world.destroyBlock(pos,true); 

	
		super.neighborChanged(state, world, pos, p_220069_4_, p_220069_5_, p_220069_6_);
		}

	
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return TileEntitiesInit.DRIER_TE.get().create(p_153215_,p_153216_);
	//	return null;
	}
	
	

	
	

}