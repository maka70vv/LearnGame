package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.InteractiveTileObject;
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.Mario;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case MyGdxGame.MARIO_HEAD_BIT | MyGdxGame.BRICK_BIT:
            case MyGdxGame.MARIO_HEAD_BIT | MyGdxGame.COIN_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Mario) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Mario) fixB.getUserData());
                break;
            case MyGdxGame.ENEMY_HEAD_BIT | MyGdxGame.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead((Mario) fixB.getUserData());
                else
                    ((Enemy) fixB.getUserData()).hitOnHead((Mario) fixA.getUserData());
                break;
            case MyGdxGame.ENEMY_BIT | MyGdxGame.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MyGdxGame.ENEMY_BIT | MyGdxGame.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).onEnemyHit((Enemy) fixB.getUserData());
                ((Enemy) fixB.getUserData()).onEnemyHit((Enemy) fixA.getUserData());
                break;
            case MyGdxGame.MARIO_BIT | MyGdxGame.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.MARIO_BIT)
                    ((Mario) fixA.getUserData()).hit((Enemy) fixB.getUserData());
                else
                    ((Mario) fixB.getUserData()).hit((Enemy) fixA.getUserData());
                break;
            case MyGdxGame.ITEM_BIT | MyGdxGame.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ITEM_BIT)
                    ((Item) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MyGdxGame.ITEM_BIT | MyGdxGame.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ITEM_BIT)
                    ((Item) fixA.getUserData()).use((Mario) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).use((Mario) fixA.getUserData());
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
