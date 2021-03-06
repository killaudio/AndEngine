package com.pigote.baseAnd;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.pigote.baseAnd.BaseScene;
import com.pigote.baseAnd.SceneManager.SceneType;

public class SplashScene extends BaseScene{

	private Sprite splash;
	
	@Override
	public void createScene() {
		
		splash = new Sprite(0, 0, resourcesManager.splash_region, vbom){
			@Override
		    protected void preDraw(GLState pGLState, Camera pCamera){
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		};
		splash.setPosition(BaseScene.CAMERA_WIDTH/2, BaseScene.CAMERA_HEIGHT/2);
		attachChild(splash);
	}

	@Override
	public void onBackKeyPressed() {
				
	}
 
	@Override
	public SceneType getSceneType() {
		
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();		
	}

}
