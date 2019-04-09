package com.nativele.survivor;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public class MonsterGenerator {

    ArrayList<Monster> monsters;
    private String direction;
    private int sens;
    private GameplayScene scene;

    private int nbMonsterMax, limite;
    private boolean generationIsAllowed;


    public MonsterGenerator(GameplayScene scene, String direction){
        this.scene = scene;
        this.monsters = new ArrayList<Monster>();
        this.nbMonsterMax = 2;
        this.generationIsAllowed = false;
        this.direction = direction;
        this.sens = direction.equals("right") ? 1 : -1;
        this.limite = 0;
    }

    public Monster chooseMonster(){
        //tirer un nombre aléatoire
        String type;
        int pv, left, right, top, bottom;
        int nbAlea = (int)(Math.random() * ((10) + 1));

        switch(nbAlea) {
            case 0: case 1:
                type = "knight";
                pv = 3;
                break;
            case 2: case 3: case 4: case 5:
                type  = "gobelin";
                pv = 1;
                break;
            default:
                type = "golem";
                pv = 2;
        }

        if(this.direction.equals("right")){
            left = 0;
            top = Constants.SCREEN_HEIGHT - this.scene.ground.getHeight() - 200;
            right = 200;
            bottom = Constants.SCREEN_HEIGHT - this.scene.ground.getHeight();
        } else{
            left = Constants.SCREEN_WIDTH - 200;
            top = Constants.SCREEN_HEIGHT - this.scene.ground.getHeight() - 200;
            right = Constants.SCREEN_WIDTH;
            bottom = Constants.SCREEN_HEIGHT - this.scene.ground.getHeight();
        }

        return new Monster(this.scene, this, new Rect(left, top, right, bottom), type, this.direction, pv);
    }


    public void generate(){

        if(monsters.size() > 0) {

            int distanceLimite = 200 + (int)(Math.random() * ((300 - 200) + 1));
            Monster lastMonster = monsters.get(monsters.size()-1);
            boolean didLastMonsterPassedLimit;
            if(this.direction.equals("right")){
                this.limite = distanceLimite;
                didLastMonsterPassedLimit = (lastMonster.getRectangle().centerX() > this.limite);
            } else {
                this.limite = Constants.SCREEN_WIDTH - distanceLimite;
                didLastMonsterPassedLimit = (lastMonster.getRectangle().centerX() < this.limite);
            }


            if( (monsters.size() < nbMonsterMax) && didLastMonsterPassedLimit ){
                generationIsAllowed = true;
            } else {
                generationIsAllowed = false;
            }
        } else {
            generationIsAllowed = true;
        }


        if(generationIsAllowed) {
            Monster monster = chooseMonster();
            this.monsters.add(monster);
            this.generationIsAllowed = false;
        }
    }


    public void draw(Canvas canvas){
        for(int i=0; i<monsters.size(); i++){
            monsters.get(i).draw(canvas);
        }
    }

    public void update(){
        generate();

        for(int i=0; i<monsters.size(); i++){
            monsters.get(i).update();
        }
    }




}
