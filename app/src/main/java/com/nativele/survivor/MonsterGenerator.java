package com.nativele.survivor;

/*
 * Classe MonsterGenerator
 *
 * Classe qui s'occupe de générer des monstres dans une direction.
 *
 * */

import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;


public class MonsterGenerator {

    private ArrayList<Monster> monsters;
    private String direction;
    private GameplayScene scene;
    private int nbMonsterMax;
    private boolean generationIsAllowed;


    public MonsterGenerator(GameplayScene scene, String direction){
        this.scene = scene;
        this.monsters = new ArrayList<>();
        this.nbMonsterMax = 3;
        this.generationIsAllowed = false;
        this.direction = direction;
    }

    /*
     *
     * Tirage aléatoire pour déterminer le type de monstre à générer
     * RETOUR : Un monstre.
     *
     */
    public Monster chooseMonster(){
        String type;
        int pv, left, right, top, bottom;
        int nbAlea = (int)(Math.random() * ((10) + 1));

        switch(nbAlea) {
            case 0: case 1:
                type = "knight";
                pv = 3;
                break;
            case 2: case 3: case 4: case 5: case 6:
                type  = "gobelin";
                pv = 1;
                break;
            default:
                type = "golem";
                pv = 2;
        }
        int width = 200;
        int height = 200;
        if(this.direction.equals("right")){
            left = 0;
            top = Constants.SCREEN_HEIGHT - this.scene.getGround().getHeight()+20 - height;
            right = width;
            bottom = Constants.SCREEN_HEIGHT - this.scene.getGround().getHeight() + 20;
        } else{
            left = Constants.SCREEN_WIDTH - width;
            top = Constants.SCREEN_HEIGHT - this.scene.getGround().getHeight() +20 - height;
            right = Constants.SCREEN_WIDTH;
            bottom = Constants.SCREEN_HEIGHT - this.scene.getGround().getHeight() + 20;
        }

        return new Monster(this.scene, this, new Rect(left, top, right, bottom), type, this.direction, pv);
    }

    /*
     * Fonction qui détermine quand un nouveau monstre doit être générer et le génère.
     */
    public void generate(){

        if(monsters.size() > 0) { //Si il y a au moins un monstre déjà généré

            //On détermine une limite dans un intervale aléatoire. Lorsque le dernier monstre généré dépasse cette limite,
            //on peut de nouveau en générer un autre, si le nombre de monstre maximum autorisé n'est pas atteint.
            int distanceLimite = 200 + (int)(Math.random() * ((300 - 200) + 1));
            Monster lastMonster = monsters.get(monsters.size()-1);
            boolean didLastMonsterPassedLimit;

            if(this.direction.equals("right")){
                didLastMonsterPassedLimit = (lastMonster.getRectangle().centerX() > distanceLimite);
            } else {
                didLastMonsterPassedLimit = (lastMonster.getRectangle().centerX() < Constants.SCREEN_WIDTH - distanceLimite);
            }


            if( (monsters.size() < nbMonsterMax) && didLastMonsterPassedLimit ){
                generationIsAllowed = true;
            } else {
                generationIsAllowed = false;
            }
        } else { //Si il n'y a aucun monstre généré
            generationIsAllowed = true;
        }

        if(generationIsAllowed) {
            Monster monster = chooseMonster();
            this.monsters.add(monster);
            this.generationIsAllowed = false;
        }
    }
    /*
     * Vide la liste des monstres
     */
    public void cleanMonsters(){
        monsters.clear();
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

    //accesseurs
    public ArrayList<Monster> getMonsters(){
        return monsters;
    }

}
