package com.pigote.objectpool;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;

import com.pigote.objectpool.SceneManager.SceneType;

public class GameScene extends BaseScene{
	
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
				if (pSceneTouchEvent.isActionUp()){
					
					ResourcesManager.getInstance().spritePool.borrowResource(SceneManager.getInstance().getCurrentScene());
				}
				return true;
			}
		};
		getResourcesButton.setAlpha(0.5f);
		this.registerTouchArea(getResourcesButton);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		
		final Text poolCount = new Text(250, 240, ResourcesManager.getInstance().font, 
				"Objects in pool:", "Objects in pool: XXXX".length(), ResourcesManager.getInstance().vbom);
		
		this.attachChild(poolCount);
		this.attachChild(getResourcesButton);
		
		this.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				ResourcesManager.getInstance().spritePool.clean();
				poolCount.setText("Objects in pool: "+ResourcesManager.getInstance().spritePool.getObjectCount());
			}
		}));

	}
	
	public void setResourcesButtonAlpha(float pAlpha){
		this.getResourcesButton.setAlpha(pAlpha);
	}
}
