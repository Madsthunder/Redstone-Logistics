package continuum.redstonelogistics.client.model;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import continuum.essentials.block.BlockConnectable;
import continuum.essentials.client.state.ITextured;
import continuum.redstonelogistics.blocks.BlockWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelUVLock;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ModelWire implements IModel, IResourceManagerReloadListener
{
	private static IModel core;
	private static IModel stub_north;
	private static IModel stub_south;
	private static IModel stub_west;
	private static IModel stub_east;
	
	public ModelWire()
	{
		
	}
	
	@Override
	public Collection<ResourceLocation> getDependencies()
	{
		return Lists.newArrayList();
	}
	
	@Override
	public Collection<ResourceLocation> getTextures()
	{
		return Lists.newArrayList(new ResourceLocation("redlogistics", "blocks/redstone_wire_off"));
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> function)
	{
		return new BakedModelWire(format, function, state);
	}
	
	@Override
	public IModelState getDefaultState()
	{
		return ModelRotation.X0_Y0;
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager)
	{
		core = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("redlogistics", "block/wire_core"));
		stub_north = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("redlogistics", "block/wire_stub_north"));
		stub_south = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("redlogistics", "block/wire_stub_south"));
		stub_west = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("redlogistics", "block/wire_stub_west"));
		stub_east = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("redlogistics", "block/wire_stub_east"));
	}
	
	private static class BakedModelWire implements IBakedModel
	{
		private static Boolean checked;
		private final VertexFormat format;
		private final Function<ResourceLocation, TextureAtlasSprite> function;
		private IModelState state;
		private IExtendedBlockState eState;
		private ImmutableMap<String, String> textures;
		private List<IBakedModel> stubs = Lists.newArrayList();
		private List<BakedQuad> quads = Lists.newArrayList();
		
		public BakedModelWire(VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> function, IModelState state)
		{
			this.format = format;
			this.function = function;
		}
		
		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
		{
			this.quads.clear();
			if(state instanceof IExtendedBlockState && state.getBlock() instanceof ITextured && modelChecks())
			{
				this.eState = (IExtendedBlockState)state;
				this.textures = new ImmutableMap.Builder<String, String>().put("wire", ((ITextured)state.getBlock()).getTexture(state).toString()).build();
				switch(state.getValue(BlockWire.direction))
				{
					case DOWN :
						this.state = ModelRotation.X0_Y0;
						/** North */
						this.addToList(stub_north, 2);
						/** South */
						this.addToList(stub_south, 3);
						/** West */
						this.addToList(stub_west, 4);
						/** East */
						this.addToList(stub_east, 5);
						break;
					case UP :
						this.state = ModelRotation.X180_Y0;
						/** North */
						this.addToList(stub_south, 2);
						/** South */
						this.addToList(stub_north, 3);
						/** West */
						this.addToList(stub_west, 4);
						/** East */
						this.addToList(stub_east, 5);
						break;
					case NORTH :
						this.state = ModelRotation.X270_Y0;
						/** Down */
						this.addToList(stub_south, 0);
						/** Up */
						this.addToList(stub_north, 1);
						/** West */
						this.addToList(stub_west, 4);
						/** East */
						this.addToList(stub_east, 5);
						break;
					case SOUTH :
						this.state = ModelRotation.X90_Y0;
						/** Down */
						this.addToList(stub_north, 0);
						/** Up */
						this.addToList(stub_south, 1);
						/** West */
						this.addToList(stub_west, 4);
						/** East */
						this.addToList(stub_east, 5);
						break;
					case WEST :
						this.state = ModelRotation.X90_Y90;
						/** Down */
						this.addToList(stub_north, 0);
						/** Up */
						this.addToList(stub_south, 1);
						/** North */
						this.addToList(stub_west, 2);
						/** South */
						this.addToList(stub_east, 3);
						break;
					case EAST :
						this.state = ModelRotation.X90_Y270;
						/** Down */
						this.addToList(stub_north, 0);
						/** Up */
						this.addToList(stub_south, 1);
						/** North */
						this.addToList(stub_west, 2);
						/** South */
						this.addToList(stub_east, 3);
						break;
					default:
						this.stubs.clear();
						this.state = ModelRotation.X0_Y0;
				}
				this.quads.addAll(this.bakeModel(core).getQuads(state, side, rand));
				for (IBakedModel stub : this.stubs)
					this.quads.addAll(stub.getQuads(state, side, rand));
				this.stubs.clear();
			}
			return this.quads;
		}
		
		private void addToList(IModel stub, Integer index)
		{
			if(this.eState.getValue(BlockConnectable.isConectedUnlisted[index]))
				this.stubs.add(this.bakeModel(stub));
		}
		
		private IBakedModel bakeModel(IModel model)
		{
			return ((IModelUVLock)((IRetexturableModel)model).retexture(this.textures)).uvlock(true).bake(this.state, this.format, this.function);
		}
		
		private static Boolean modelChecks()
		{
			return checked == null ? checked = checkUVR(core, stub_north, stub_south, stub_west, stub_east) : checked;
		}
		
		private static Boolean checkUVR(IModel... models)
		{
			Boolean b = true;
			IModel missing = ModelLoaderRegistry.getMissingModel();
			for (IModel model : models)
				if(b)
					if(!(model != missing && model instanceof IRetexturableModel && model instanceof IModelUVLock))
						b = false;
			return b;
		}
		
		@Override
		public boolean isAmbientOcclusion()
		{
			return true;
		}
		
		@Override
		public boolean isGui3d()
		{
			return true;
		}
		
		@Override
		public boolean isBuiltInRenderer()
		{
			return false;
		}
		
		@Override
		public TextureAtlasSprite getParticleTexture()
		{
			return Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
		}
		
		@Override
		public ItemCameraTransforms getItemCameraTransforms()
		{
			return ItemCameraTransforms.DEFAULT;
		}
		
		@Override
		public ItemOverrideList getOverrides()
		{
			return ItemOverrideList.NONE;
		}
	}
}
