package continuum.redstonelogistics.client.model;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public class ModelBattery implements IModel
{
	@Override
	public Collection<ResourceLocation> getDependencies()
	{
		return Lists.newArrayList();
	}
	
	@Override
	public Collection<ResourceLocation> getTextures()
	{
		return Lists.newArrayList(new ResourceLocation("metalextras", "blocks/bronze_block"), new ResourceLocation("blocks/wool_colored_silver"), new ResourceLocation("blocks/wool_colored_blue"), new ResourceLocation("blocks/wool_colored_orange"), new ResourceLocation("blocks/wool_colored_purple"));
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{
		return null;
	}
	
	@Override
	public IModelState getDefaultState()
	{
		return ModelRotation.X0_Y0;
	}
}
