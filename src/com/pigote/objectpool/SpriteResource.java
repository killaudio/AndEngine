package com.pigote.objectpool;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class SpriteResource {
	
	private BitmapTextureAtlas texSprite;
	private TiledTextureRegion regSprite;
	private AnimatedSprite  sprite = null;
	 
	private TextureManager textureManager;
	
	private static int SPR_COLUMN = 4;
	private static int SPR_ROWS = 2;
	
	public SpriteResource(){
		this.textureManager = ResourcesManager.getInstance().activity.getTextureManager();
		loadSprite();
	}
	
	public boolean isValid() {
		if (sprite == null)
			return false;
		return true;		
	}

	public AnimatedSprite getSprite() {
		// TODO Auto-generated method stub
		return sprite;
	}
	
	private void loadSprite(){
		texSprite = new BitmapTextureAtlas(textureManager, 256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		regSprite = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texSprite,
				ResourcesManager.getInstance().activity.getAssets(),"spr_banana.png", 0, 0, SPR_COLUMN, SPR_ROWS);
		texSprite.load();
		sprite = new AnimatedSprite(0, 0, regSprite, ResourcesManager.getInstance().vbom);
	}
}
