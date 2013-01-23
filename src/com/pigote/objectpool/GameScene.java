package com.pigote.objectpool;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;

import com.pigote.objectpool.SceneManager.SceneType;

public class GameScene extends BaseScene{
	
	private SpritePool spritePool;
	private SpriteResource sr;
	private Sprite getResourcesButton;
	
	@Override
	public void createScene() {
		createBackground();
		populateScene();
		
	}

	@Override
	public void onBackKeyPressed() {
		//SceneManager.getInstance().setScene(SceneType.SCENE_MENU);
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
	}
	
	private void createBackground()
	{
	    attachChild(new Sprite(0, 0, resourcesManager.game_background_region, vbom)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    });
	}
	
	private void populateScene() {
		getResourcesButton = new Sprite (0, CAMERA_HEIGHT - resourcesManager.getResource_region.getHeight(),
				resourcesManager.getResource_region, ResourcesManager.getInstance().vbom){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				this.setAlpha(0.4f);
				this.setAlpha(1.0f);
				return true;
			}
		};
		
		spritePool = new SpritePool();
		sr = spritePool.borrowResource();
		AnimatedSprite as = sr.getSprite();
		as.setScale(3, 3);
		this.attachChild(as);
		this.attachChild(getResourcesButton);
		as.animate(100);
		this.registerTouchArea(as);
		this.registerTouchArea(getResourcesButton);
		this.setTouchAreaBindingOnActionDownEnabled(true);		
	}
}
