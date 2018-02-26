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
import gdx.menu.images.Wall;

public class ScrAniHit implements Screen, InputProcessor {

    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnMenu,btnQuit;
    TextureRegion trTemp;
    Texture txSheet, txNamAH, txNamGame;
    Sprite sprNamAH, sprDude, sprAni, sprMouse;   //sprAni is a ghost, a sprite used for hit detection
    int nFrame, nPos, nX = 100, nY = 100;   //nX and nY coordinates for sprAni  hv
    Animation araniDude[], araniMouse[];
    int fW, fH, fSx, fSy;
    Wall[] arWall = new Wall[4];
    boolean arbDirection[] = new boolean[4];
    float fSpeed = 1;

    public ScrAniHit(GamMenu _gamMenu) {
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
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        txSheet = new Texture("sprmouse.png");

        txNamAH = new Texture("A.jpg");
        sprNamAH = new Sprite(txNamAH); //Screen Name sprite
        sprNamAH.setFlip(false, true);
        sprNamAH.setSize(60, 80);
        sprNamAH.setPosition(Gdx.graphics.getWidth() / 2 - 30, Gdx.graphics.getHeight() / 2 - 40);
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 50);   //Top Wall
        arWall[1] = new Wall(Gdx.graphics.getWidth(), 50, 0, Gdx.graphics.getHeight() - 100);    //Bottom Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 200, 0, 100);   //Left Wall
        arWall[3] = new Wall(50, Gdx.graphics.getHeight() - 200, Gdx.graphics.getWidth() - 50, 100);    //Right Wall
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
        sprAni = new Sprite(txNamAH, 0, 0, fW, fH);
        sprAni.setPosition(200, 200);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); //White background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fSx = sprAni.getX();
        float fSy = sprAni.getY();
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
            sprAni.setX(sprAni.getX() - fSpeed);
            nX = nX -= fSpeed;
            nPos = 1;
            nFrame++;
        }
        if (arbDirection[1] == true) {
            sprAni.setX(sprAni.getX() + fSpeed);
            nX = nX += fSpeed;
            nPos = 2;
            nFrame++;
        }
        if (arbDirection[2] == true) {
            sprAni.setY(sprAni.getY() - fSpeed);
            nY = nY -= fSpeed;
            nPos = 3;
            nFrame++;
        }
        if (arbDirection[3] == true) {
            sprAni.setY(sprAni.getY() + fSpeed);
            nY = nY += fSpeed;
            nPos = 0;
            nFrame++;
        }
        for (int i = 0; i < arWall.length; i++) {
            if (isHitS(sprAni, arWall[i])) {
                sprAni.setPosition(fSx, fSy);
            }
        }
        if (isHitS(sprAni, sprNamAH)) {
            sprAni.setPosition(fSx, fSy);
        }

        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        btnMenu.draw(batch);
        btnQuit.draw(batch);
        sprNamAH.draw(batch);
        batch.draw(trTemp, fSx, fSy);
        for (int i = 0; i < arWall.length; i++) {
            arWall[i].draw(batch);
        }
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
        txNamAH.dispose();
        txSheet.dispose();
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
            if (isHitB(screenX, screenY, btnMenu)) {
                gamMenu.updateState(0);
                System.out.println("Hit Menu");
            } else if (isHitB(screenX, screenY, btnQuit)) {
                System.out.println("Quit");
                System.exit(0);
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

    public boolean isHitB(int nX, int nY, Sprite sprBtn) {
        if (nX > sprBtn.getX() && nX < sprBtn.getX() + sprBtn.getWidth() && nY > sprBtn.getY() && nY < sprBtn.getY() + sprBtn.getHeight()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHitS(Sprite spr1, Sprite spr2) {
        return spr1.getBoundingRectangle().overlaps(spr2.getBoundingRectangle());
    }
}
