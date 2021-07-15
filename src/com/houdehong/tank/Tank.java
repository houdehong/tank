package com.houdehong.tank;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

/**
 * @author houdehong
 * @date 2021-07-07
 * @description 抽象出坦克类，并设置 坦克本身属性 速度 坐标 位置
 */
public class Tank {
    private static final int SPEED = 5;
    private int x ;
    private int y;
    private Dir dir = Dir.DOWN;
    private boolean moving = false;
    public static final int HEIGHT = ResourceMgr.goodTankD.getHeight();
    public static final int WIDTH = ResourceMgr.goodTankD.getWidth();
    private boolean isLive = true;
    private Group group = Group.BAD;
    private Random random = new Random();
    Rectangle t = new Rectangle();
    /**
     * 让坦克持有窗口对象的引用
     */
    private TankFrame tf = null;

    public Tank(int x, int y, Dir dir, TankFrame tf, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
        t.x = this.x;
        t.y = this.y;
        t.height = HEIGHT;
        t.width = WIDTH;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics g) {
        if(!isLive){
            tf.tanks.remove(this);
        }
       /* Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x,y,80,80);
        g.setColor(c);*/
        switch (dir){
            case UP:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankU : ResourceMgr.goodTankU, x, y, null);
                break;
            case LEFT:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankL : ResourceMgr.goodTankL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankR : ResourceMgr.goodTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankD : ResourceMgr.goodTankD, x, y, null);
                break;
            default:
                break;
        }
      //  if(this.group == Group.BAD){
            move();
     //   }

    }

    private void move() {
        if(moving){
            switch (dir){
                case UP: y -= SPEED;
                    break;
                case DOWN: y += SPEED;
                    break;
                case LEFT: x -= SPEED;
                    break;
                case RIGHT: x += SPEED;
                    break;
                default:
                    break;
            }
        }
        // 设置控制敌方坦克随机发射子弹
        if(this.group == Group.BAD && random.nextInt(100) > 95){
            this.fire();
        }
        // 设置控制敌方坦克随机控制方向
        if(this.group == Group.BAD && random.nextInt(100) > 95){
            changeDir();
        }
        // 增加边界判定
        decisionBoundary();

        // 移动之后更新rect的坐标
        t.x = this.x;
        t.y = this.y;

    }

    private void decisionBoundary() {
        if(this.x > TankFrame.GAME_WIDTH  - WIDTH - 2){
            this.x = TankFrame.GAME_WIDTH - WIDTH;
        }
        if(this.x <= 0){
            this.x = 0;
        }
        if(this.y > TankFrame.GAME_HEIGHT - HEIGHT - 2){
            this.y = TankFrame.GAME_HEIGHT  - HEIGHT;
        }
        if(this.y <= 0){
            this.y = 6;
        }
    }

    private void changeDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    /**
     * 子弹，和坦克的坐标以及方向保持一致，但是子弹是由窗口绘制的，
     * 所以TankFrame的实例的引用通过坦克的构造方法传进来，
     * 利用坦克的坐标和位置定义子弹的位置
     */
    public void fire() {
        tf.bullets.add(new Bullet(this.x + WIDTH/2 - Bullet.WIDTH/2, this.y +HEIGHT/2 - Bullet.WIDTH/2 , this.dir, tf, this.group));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    // 坦克死亡
    public void die() {
        isLive = false;
    }

    public Rectangle getT() {
        return t;
    }

    public void setT(Rectangle t) {
        this.t = t;
    }
}
