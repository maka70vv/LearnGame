package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;

public class Brick extends InteractiveTileObject{
    public Brick(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {
        if (mario.isBig()) {
            Gdx.app.log("Brick", "Collision");
            setCategoryFilter(MyGdxGame.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(200);
            MyGdxGame.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        } else {
            MyGdxGame.manager.get("audio/sounds/bump.wav", Sound.class).play();

        }
    }
}
