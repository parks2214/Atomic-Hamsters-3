package gdx.menu.Screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gdx.menu.GamMenu;
import gdx.menu.images.Button;

public class ScrFood implements Screen, InputProcessor {

    boolean arbDirection[] = new boolean[4];
    Button btnMenu, btnQuit;
    OrthographicCamera oc;
    Texture txNamS, txHamP, txBox1, txBox2, txSheet;
    GamMenu gamMenu;
    SpriteBatch batch;
    Sprite sprNamSign, sprHamP, sprBox1, sprBox2, sprMouse, sprAni;
    int nTrig = 0; //Trigger variable for sign
    int nFrame, nPos, nW, nH, nSx, nSy, nX = 100, nY = 100;
    float fSpeed = 1;
    Animation araniMouse[];
    TextureRegion trTemp;

    public ScrFood(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        batch = new SpriteBatch();
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        txNamS = new Texture("S.png");
        txSheet = new Texture("sprmouse.png");
        sprNamSign = new Sprite(txNamS);
        sprNamSign.setFlip(false, true);
        sprNamSign.setSize(60, 80);
        sprNamSign.setPosition(Gdx.graphics.getWidth() / 2 - 30, Gdx.graphics.getHeight() / 2 - 40);
        txHamP = new Texture("HamP.png");
        sprHamP = new Sprite(txHamP);
        sprHamP.setPosition(150, 150);
        sprHamP.setSize(50, 50);
        sprHamP.setFlip(false, true);
        txBox1 = new Texture("Textbox.png");
        sprBox1 = new Sprite(txBox1);
        sprBox1.setSize(300, 125);
        sprBox1.setPosition(Gdx.graphics.getWidth() / 2 - sprBox1.getWidth() / 2, 0);
        sprBox1.setFlip(false, true);
        txBox2 = new Texture("Textbox2.png");
        sprBox2 = new Sprite(txBox2);
        sprBox2.setPosition(Gdx.graphics.getWidth() / 2 - sprBox1.getWidth() / 2, 0);
        sprBox2.setSize(300, 125);
        sprBox2.setFlip(false, true);
        //Direction sets
        arbDirection[0] = false;
        arbDirection[1] = false;
        arbDirection[2] = false;
        arbDirection[3] = false;
        //Animation stuff
        nFrame = 0;
        nPos = 0;
        araniMouse = new Animation[4];
        nW = txSheet.getWidth() / 4;
        nH = txSheet.getHeight() / 4;
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprMouse = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                nSx = j * nW;
                nSy = i * nH;
                sprMouse = new Sprite(txSheet, nSx, nSy, nW, nH);
                sprMouse.setFlip(false, true);
                arSprMouse[j] = new Sprite(sprMouse);
            }
            araniMouse[i] = new Animation(0.8f, arSprMouse);

        }
        sprAni = new Sprite(txNamS, 0, 0, nW, nH);
        sprAni.setPosition(200, 200);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float Delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1); //Blue background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fSx = sprAni.getX();
        float fSy = sprAni.getY();
        //Animation Stuff
        if (nFrame > 7) {
            nFrame = 0;
        }
        trTemp = araniMouse[nPos].getKeyFrame(nFrame, false);
        if (isHitS(sprAni, sprHamP) && nTrig == 0) {
            System.out.println("He hecking ate it");
            fSpeed += 0.1f;
            System.out.println(fSpeed);
            nTrig = 1;
        } else if (isHitS(sprAni, sprHamP) && nTrig == 3) {
            nTrig = 3;
        } else if (!isHitS(sprAni, sprHamP)) {
            nTrig = 0;
        }
        if (nTrig == 1 && Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            nTrig = 3;
        }
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


        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        btnMenu.draw(batch);
        sprNamSign.draw(batch);
        sprHamP.draw(batch);
        btnQuit.draw(batch);
        batch.draw(trTemp, fSx, fSy);
        if (nTrig == 1) {
            sprBox1.draw(batch);
        } else if (nTrig == 3) {
            sprBox2.draw(batch);
        }
        batch.end();
    }

    /*
     * UpdateState(0) for Menu
     * UpdateState(1) for Play
     */
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
        txNamS.dispose();
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
