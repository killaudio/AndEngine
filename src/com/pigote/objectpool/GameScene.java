package com.pigote.objectpool;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.color.Color;

import com.pigote.objectpool.SceneManager.SceneType;

public class GameScene extends BaseScene{
	
	private SpritePool spritePool;
	private SpriteResource sr;
	@Override
	public void createScene() {
		this.setBackground(new Background(Color.WHITE));
		spritePool = new SpritePool();
		sr = spritePool.borrowResource();
		AnimatedSprite as = sr.getSprite();
		as.setScale(3, 3);
		this.attachChild(as);
		as.animate(100);
		this.registerTouchArea(as);
		this.setTouchAreaBindingOnActionDownEnabled(true);
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

}
