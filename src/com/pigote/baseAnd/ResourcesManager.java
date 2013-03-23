package com.pigote.baseAnd;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;
import android.graphics.Color;

import com.pigote.baseAnd.MainActivity;

public class ResourcesManager
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final ResourcesManager INSTANCE = new ResourcesManager();
    
    public Engine engine;
    public MainActivity activity;
    public BoundCamera camera;
    public VertexBufferObjectManager vbom;
    public Font font;
    
    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------
    
    public ITextureRegion splash_region;
    public ITextureRegion menu_background_region;
    public ITextureRegion play_region;
    public ITextureRegion exit_region;
    public ITextureRegion game_background_region;
    
    public ITextureRegion hold1_region;
    public ITextureRegion hold2_region;
    
    //climber dude
    public ITextureRegion m_Head;
    public ITextureRegion m_Torso1;
    public ITextureRegion m_Torso2;
    public ITextureRegion m_Torso3;
    public ITextureRegion m_UpperArmLeft;
    public ITextureRegion m_UpperArmRight;
    public ITextureRegion m_LowerArmLeft;
    public ITextureRegion m_LowerArmRight;
    public ITextureRegion m_UpperLegLeft;
    public ITextureRegion m_UpperLegRight;
    public ITextureRegion m_LowerLegLeft;
    public ITextureRegion m_LowerLegRight;
    public ITextureRegion m_DebugTexture;
    
    public ITextureRegion player_region;
    
    private BuildableBitmapTextureAtlas gameTextureAtlas;
    private BuildableBitmapTextureAtlas climberTextureAtlas;
    private BitmapTextureAtlas splashTextureAtlas;
    private BuildableBitmapTextureAtlas menuTextureAtlas;
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    public void loadSplashScreen()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 484, 137, TextureOptions.BILINEAR);
    	splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
    	splashTextureAtlas.load();
    }
    
    public void loadMenuResources()
    {
        loadMenuGraphics();
        loadMenuAudio();
        loadMenuFonts();
    }
    
    public void loadGameResources()
    {
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }
    
    public void loadMenuTextures()
    {
        menuTextureAtlas.load();
    }
    
    public void unloadSplashScreen()
    {
    	splashTextureAtlas.unload();
    	splash_region = null;
    }
    
    public void unloadGameTextures()
    {
    	gameTextureAtlas.unload();
    	climberTextureAtlas.unload();
    }

    public void unloadMenuTextures()
    {
        menuTextureAtlas.unload();
    }

    public static void prepareManager(Engine engine, MainActivity activity, BoundCamera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    private void loadMenuGraphics()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
    	play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
    	exit_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "exit.png");
    	       
    	try 
    	{
    	    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.menuTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    }
    
    private void loadGameGraphics()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
    	gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
    	game_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_background.png");
    	hold1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "hold1.png");
    	hold2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "hold2.png");
    	player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "player.png", 3, 1);
    	
    	//climber
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/climber");
    	climberTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);

        m_Head = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "head.png");
        m_Torso1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "torso1.png");
        m_Torso2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "torso2.png");
        m_Torso3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "torso3.png");
        m_UpperArmLeft = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "upper_arm_left.png");
        m_UpperArmRight = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "upper_arm_right.png");
        m_LowerArmLeft = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "lower_arm_left.png");
        m_LowerArmRight = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "lower_arm_right.png");
        m_UpperLegLeft = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "upper_leg_left.png");
        m_UpperLegRight = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "upper_leg_right.png");
        m_LowerLegLeft = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "lower_leg_left.png");
        m_LowerLegRight = BitmapTextureAtlasTextureRegionFactory.createFromAsset(climberTextureAtlas, activity, "lower_leg_right.png");

        try 
    	{
    	    this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.gameTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    	
    	try 
    	{
    	    this.climberTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.climberTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    }
    
    private void loadGameAudio()
    {
        
    }
    
    private void loadMenuAudio()
    {
        
    }
    
    private void loadMenuFonts(){
    	FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }

    private void loadGameFonts()
    {
        
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourcesManager getInstance()
    {
        return INSTANCE;
    }
    
}