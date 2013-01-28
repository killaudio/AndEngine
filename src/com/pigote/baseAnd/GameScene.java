package com.pigote.baseAnd;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.util.GLState;
import org.andengine.util.HorizontalAlign;

import com.badlogic.gdx.math.Vector2;
import com.pigote.baseAnd.SceneManager.SceneType;

public class GameScene extends BaseScene{
	
	private PhysicsWorld physicsWorld;
	
	private HUD gameHUD;
	private Text scoreText;
	private int score = 0;
	
	private void createBackground(){
		//setBackground(new Background(Color.BLUE));
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
	
	private void createHUD(){
		gameHUD = new HUD();
		//initializing score text with all the chars it'll use
		scoreText = new Text(20, 420, resourcesManager.font, "Score: 0123456789", vbom);
		scoreText.setHorizontalAlign(HorizontalAlign.LEFT);
		//scoreText.setAnchorCenter(0, 0);    
		scoreText.setText("Score: 0");
		gameHUD.attachChild(scoreText);
		camera.setHUD(gameHUD);
	}

	private void addToScore(int i){
		score +=i;
		scoreText.setText("Score: " + score);
	}
	
	private void createPhysics()
	{
		//mod gravity vector accordingly to affect gravity(new Vector2(0, -17))
	    physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -17), false); 
	    registerUpdateHandler(physicsWorld);
	}
	
	@Override
	public void createScene() {
	    createBackground();
	    createHUD();
	    createPhysics();
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMenuScene(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene()
	{
	    camera.setHUD(null);
	    camera.setCenter(CAMERA_WIDTH/2, CAMERA_HEIGHT/2);

	    // removing all game scene objects.
	}
	
}
