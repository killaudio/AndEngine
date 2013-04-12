package com.pigote.baseAnd;

import java.io.IOException;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pigote.baseAnd.SceneManager.SceneType;

public class GameScene extends BaseScene implements IOnSceneTouchListener, IOnAreaTouchListener{
	
	private PhysicsWorld physicsWorld;
	
	private HUD gameHUD;
	private Text scoreText;
	private int score = 0;
	
	private Climber climber;
	
    private MouseJoint mouseJointActive;
    private Body groundBody;
    private Body holdL;
    private Body holdR;
    
	private Text gameOverText;
	private boolean gameOverDisplayed = false;
    
    //---------------------------------------------
    // Level loader stuff
    //---------------------------------------------

    private static final String TAG_ENTITY = "entity";
    private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
    private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
    private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";
        
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HOLD1 = "hold1";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HOLD2 = "hold2";
    public static enum limb {LARM_UP, LARM_LOW, RARM_UP, RARM_LOW,
    								LLEG_UP, LLEG_LOW, RLEG_UP, RLEG_LOW}

	private void createBackground(){
		setBackground(new Background(Color.BLUE));
	}
	
	private void createHUD(){
		gameHUD = new HUD();
		//initializing score text with all the chars it'll use
		scoreText = new Text(0, 0, resourcesManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT) ,vbom);
		scoreText.setAnchorCenter(0, 0);    
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
	    physicsWorld.setContactListener(contactListener());
	    registerUpdateHandler(physicsWorld);
	}
	
    private void loadLevel(int levelID)
    {
        final SimpleLevelLoader levelLoader = new SimpleLevelLoader(vbom);
        
        levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL)
        {
            public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException 
            {
                final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
                final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
                
                camera.setBounds(0, 0, width, height); // here we set camera bounds
                camera.setBoundsEnabled(true);

                return GameScene.this;
            }
        });
        
        levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY)
        {
            public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
            {
                final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
                final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
                final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
                
                final Sprite levelObject;
                
                if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HOLD1))
                {
                	levelObject = new Hold(x, y, resourcesManager.hold1_region, vbom, 100, physicsWorld);
                } 
                else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HOLD2))
                {
                    levelObject = new Hold(x, y, resourcesManager.hold2_region, vbom, 100, physicsWorld);
                }
                else
                {
                    throw new IllegalArgumentException();
                }

                levelObject.setCullingEnabled(true);

                return levelObject;
            }
        });

        levelLoader.loadLevelFromAsset(activity.getAssets(), "walls/" + levelID + ".lvl");
    }
	
    private void createGameOverText(){
        gameOverText = new Text(0, 0, resourcesManager.font, "Game Over!", vbom);
    }

    private void displayGameOverText(){
        camera.setChaseEntity(null);
        gameOverText.setPosition(camera.getCenterX(), camera.getCenterY());
        attachChild(gameOverText);
        gameOverDisplayed = true;
    }
    
    private ContactListener contactListener(){
        ContactListener contactListener = new ContactListener(){
            public void beginContact(Contact contact){
                final Fixture x1 = contact.getFixtureA();
                final Fixture x2 = contact.getFixtureB();
                if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null){
	                if (x1.getBody().getUserData().equals("hold") && 
	                		(x2.getBody().getUserData().equals("handL") || x2.getBody().getUserData().equals("handR"))){
	                	addToScore(1);
	                	if (x2.getBody().getUserData().equals("handL"))
	                	holdL = x1.getBody(); else holdR = x1.getBody();
	                } else if (x1.getBody().getUserData().equals("hold") && x2.getBody().getUserData().equals("foot")) {
	                	addToScore(-1);
	                }
                }
            }

            public void endContact(Contact contact){
            	final Fixture x1 = contact.getFixtureA();
                final Fixture x2 = contact.getFixtureB();
                if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null){
	                if (x1.getBody().getUserData().equals("hold") && 
	                		(x2.getBody().getUserData().equals("handL") || x2.getBody().getUserData().equals("handR"))){
	                	addToScore(10); //destroy joint
	                	if (x2.getBody().getUserData().equals("handL"))
		                	holdL = null; else holdR = null;
	                } else if (x1.getBody().getUserData().equals("hold") && x2.getBody().getUserData().equals("foot")) {
	                	addToScore(-10); //Nothing to do here I think
	                }
                }               
            }

            public void preSolve(Contact contact, Manifold oldManifold){

            }

            public void postSolve(Contact contact, ContactImpulse impulse){

            }
        };
        return contactListener;
    }
    
	@Override
	public void createScene() {
	    createBackground();
	    createHUD();
	    createPhysics();
	    loadLevel(1);
	    loadDebugBox(physicsWorld, this);
	    climber = new Climber(BaseScene.CAMERA_WIDTH/2, BaseScene.CAMERA_HEIGHT, vbom, camera, physicsWorld, this);
	    groundBody = this.physicsWorld.createBody(new BodyDef());
	    createGameOverText();
	    setOnSceneTouchListener(this);
	    setOnAreaTouchListener(this);
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

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX,
    		float pTouchAreaLocalY) {
    	final IShape face = (IShape) pTouchArea;
    	String ud = (String) ((Body) face.getUserData()).getUserData();
    	if(pSceneTouchEvent.isActionDown()) {
    		 // If we have an active MouseJoint, we are just moving it around
    		 // instead of creating a second one.
    		if (this.mouseJointActive == null) {
    			//this.mEngine.vibrate(100);
    			if( ud.equals("handL") || ud.equals("handR") || ud.equals("foot")){
    				climber.releaseHold((Body) face.getUserData(), physicsWorld);
    				this.mouseJointActive = this.createMouseJoint(face, pTouchAreaLocalX, pTouchAreaLocalY);
    			}
    		}
    		return true;
    	} else if(pSceneTouchEvent.isActionUp()) {
    		if (this.mouseJointActive != null) {
    			//this.mEngine.vibrate(100);
    			if(ud.equals("handL") && holdL!= null){
    				climber.grabHold(holdL, (Body) face.getUserData(), physicsWorld);
    			} else if (ud.equals("handR") && holdR!= null){
    				climber.grabHold(holdR, (Body) face.getUserData(), physicsWorld);
    			}
    		} else if (this.mouseJointActive == null) {
    			if(ud.equals("lowerArmL")){
    				climber.flex(limb.LARM_UP);
    			} else if(ud.equals("lowerArmR")){
    				climber.flex(limb.RARM_UP);
    			} else if(ud.equals("lowerLegL")){
    				climber.flex(limb.LLEG_UP);
    			} else if(ud.equals("lowerLegR")){
    				climber.flex(limb.RLEG_UP);
    			} else if(ud.equals("upperArmL")){
    				climber.relax(limb.LARM_LOW);
    			} else if(ud.equals("upperArmR")){
    				climber.relax(limb.RARM_LOW);
    			} else if(ud.equals("upperLegL")){
    				climber.relax(limb.LLEG_LOW);
    			} else if(ud.equals("upperLegR")){
    				climber.relax(limb.RLEG_LOW);
    			}
    		}
    	}
    	return false;
    }
	
    @Override
    public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
    	if (this.physicsWorld != null) {
    		switch(pSceneTouchEvent.getAction()) {

    		case TouchEvent.ACTION_MOVE:
    			if(this.mouseJointActive != null) {
    				final Vector2 vec = Vector2Pool.obtain(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
    				this.mouseJointActive.setTarget(vec);
    				Vector2Pool.recycle(vec);
    			}
    			return true;
    		case TouchEvent.ACTION_UP:
    			if(this.mouseJointActive != null) {
    				this.physicsWorld.destroyJoint(this.mouseJointActive);
    				this.mouseJointActive = null;
    			}
    			return true;
    		}
    		return false;
    	}
    	return false;
    }

    public MouseJoint createMouseJoint(final IShape pFace, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
    	final Body body = (Body) pFace.getUserData();
    	final MouseJointDef mouseJointDef = new MouseJointDef();

    	final Vector2 localPoint = Vector2Pool.obtain((pTouchAreaLocalX - pFace.getWidth() * 0.5f) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, (pTouchAreaLocalY - pFace.getHeight() * 0.5f) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
    	this.groundBody.setTransform(localPoint, 0);

    	mouseJointDef.bodyA = this.groundBody;
    	mouseJointDef.bodyB = body;
    	mouseJointDef.dampingRatio = 0.95f;
    	mouseJointDef.frequencyHz = 30f;
    	mouseJointDef.maxForce = (300.0f * body.getMass());
    	mouseJointDef.collideConnected = true;

    	mouseJointDef.target.set(body.getWorldPoint(localPoint));
    	Vector2Pool.recycle(localPoint);

    	return (MouseJoint) physicsWorld.createJoint(mouseJointDef);
    }
    
    private void loadDebugBox(PhysicsWorld m_PhysicsWorld, Scene pScene) {
		int m_CameraWidth = BaseScene.CAMERA_WIDTH;
		int m_CameraHeight = BaseScene.CAMERA_HEIGHT;
		
		final Rectangle ground = new Rectangle(m_CameraWidth / 2, 1, m_CameraWidth, 2, vbom);
		//final Rectangle roof = new Rectangle(m_CameraWidth / 2, m_CameraHeight - 1, m_CameraWidth, 2, vbom);
		final Rectangle left = new Rectangle(1, m_CameraHeight / 2, 1, m_CameraHeight, vbom);
		final Rectangle right = new Rectangle(m_CameraWidth - 1, m_CameraHeight / 2, 2, m_CameraHeight, vbom);

    	final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(1.0f, 0.2f, 0.1f);

    	//PhysicsFactory.createBoxBody(m_PhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
    	PhysicsFactory.createBoxBody(m_PhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
    	PhysicsFactory.createBoxBody(m_PhysicsWorld, left, BodyType.StaticBody, wallFixtureDef);
    	PhysicsFactory.createBoxBody(m_PhysicsWorld, right, BodyType.StaticBody, wallFixtureDef);
    	
    	pScene.attachChild(ground);
    	pScene.attachChild(left);
    	pScene.attachChild(right);
	}
	
}
