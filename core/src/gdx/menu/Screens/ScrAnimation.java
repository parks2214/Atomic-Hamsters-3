package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import gdx.menu.GamMenu;
import gdx.menu.images.Button;

public class ScrAnimation implements Screen, InputProcessor {
    Button btnMenu, btnSign, btnPlay, btnQuit, btnAH, btnGame;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Texture txButtonM, txButtonQ, txNamT, txSheet;
    Animation araniMouse[];
    TextureRegion trTemp;
    int fW, fH, fSx, fSy;
    int nFrame, nPos;
    int nX = 100, nY = 100;
    Sprite sprButtonMenu, sprButtonSign, sprNamT, sprMouse;
    SpriteBatch batch;
    boolean arbDirection[] = new boolean[4];
    float fSpeed = 1;
    
    public ScrAnimation(GamMenu _gamMenu) {
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        //Buttons
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnSign = new Button(100, 50, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50, "Food.png");
        btnPlay = new Button(100, 50, 0, Gdx.graphics.getHeight() - 50, "Play.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        btnAH = new Button(100, 50, Gdx.graphics.getWidth()/2 - 50, 0, "AniHit.png");
        btnGame = new Button(100, 50, 0, 0, "Game.png");
        
        txNamT = new Texture("A.jpg");
        txSheet = new Texture("sprmouse.png");
        sprNamT = new Sprite(txNamT);   //Screen Name sprite
        sprNamT.setFlip(false, true);
        sprNamT.setSize(60, 80);
        sprNamT.setPosition(Gdx.graphics.getWidth()/2 - 30, Gdx.graphics.getHeight()/2 - 40);
        
        //Direction sets
        arbDirection[0] = false;
        arbDirection[1] = false;
        arbDirection[2] = false;
        arbDirection[3] = false;
        //Animation Stuff
        nFrame = 0;
        nPos = 0;
        araniMouse = new Animation[4];
        fW = txSheet.getWidth() / 4;
        fH = txSheet.getHeight() / 4;
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprMouse = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                fSx = j * fW;
                fSy = i * fH;
                sprMouse = new Sprite(txSheet, fSx, fSy, fW, fH);
                sprMouse.setFlip(false, true);
                arSprMouse[j] = new Sprite(sprMouse);
            }
            araniMouse[i] = new Animation(0.8f, arSprMouse);

        }
        
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); //White background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //Animation Stuff
        
        if (nFrame > 7) {
            nFrame = 0;
        }
        trTemp = araniMouse[nPos].getKeyFrame(nFrame, false);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            arbDirection[0] = true;
            arbDirection[1] = false;
            arbDirection[2] = false;
            arbDirection[3] = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            arbDirection[0] = false;
            arbDirection[1] = true;
            arbDirection[2] = false;
            arbDirection[3] = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            arbDirection[0] = false;
            arbDirection[1] = false;
            arbDirection[2] = true;
            arbDirection[3] = false;

        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            arbDirection[0] = false;
            arbDirection[1] = false;
            arbDirection[2] = false;
            arbDirection[3] = true;
        }

        //Direction instructions
        if (arbDirection[0] == true) {
            sprMouse.setX(sprMouse.getX() - fSpeed);
            nX = nX -= fSpeed;
            nPos = 1;
            nFrame++;
        }
        if (arbDirection[1] == true) {
            sprMouse.setX(sprMouse.getX() + fSpeed);
            nX = nX += fSpeed;
            nPos = 2;
            nFrame++;
        }
        if (arbDirection[2] == true) {
            sprMouse.setY(sprMouse.getY() - fSpeed);
            nY = nY -= fSpeed;
            nPos = 3;
            nFrame++;
        }
        if (arbDirection[3] == true) {
            sprMouse.setY(sprMouse.getY() + fSpeed);
            nY = nY += fSpeed;
            nPos = 0;
            nFrame++;
        }
        
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        batch.draw(trTemp, nX, nY);
        btnMenu.draw(batch);
        btnSign.draw(batch);
        btnPlay.draw(batch);
        sprNamT.draw(batch);
        btnQuit.draw(batch);
        btnGame.draw(batch);
        btnAH.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        txNamT.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            //System.out.println(screenX +" " + screenY);
            if (isHit(screenX, screenY, btnMenu)) {
                gamMenu.updateState(0);
                System.out.println("Hit Menu");
            } else if (isHit(screenX, screenY, btnSign)) {
                gamMenu.updateState(2);
                System.out.println("Hit Sign");
            } else if (isHit(screenX, screenY, btnPlay)){
                gamMenu.updateState(1);
                System.out.println("Hit Play");
            } else if (isHit(screenX, screenY, btnQuit)){
                System.out.println("Quit");
                System.exit(0);
            } else if (isHit(screenX, screenY, btnAH)){
                System.out.println("Hit AniHit");
                gamMenu.updateState(4);
            } else if (isHit(screenX, screenY, btnGame)){
                System.out.println("Hit Game");
                gamMenu.updateState(5);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    public boolean isHit(int nX, int nY, Sprite sprBtn) {
        if (nX > sprBtn.getX() && nX < sprBtn.getX() + sprBtn.getWidth() && nY > sprBtn.getY() && nY < sprBtn.getY() + sprBtn.getHeight()) {
            return true;
        } else {
            return false;
        }
    }
}
