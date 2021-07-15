package com.houdehong.tank;

import java.awt.*;

/**
 * @author houdehong
 * @date 2021-07-07
 * @description 子弹
 */
public class Bullet{

    public static final int HEIGHT = ResourceMgr.bulletL.getHeight();
    public static final int WIDTH = ResourceMgr.bulletL.getWidth();
    private static final int SPEED = 10;
    private int x, y;
    private Dir dir;
    // 标记子弹是否存活
    private boolean isLive = true;
    TankFrame tf = null;
    private Group group = Group.BAD;
    Rectangle b = new Rectangle();

    public Bullet(int x, int y, Dir dir, TankFrame tf, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
        b.x = this.x;
        b.y = this.y;
        b.height = HEIGHT;
        b.width = WIDTH;
    }

    public void paint(Graphics g){
        // 子弹死亡之后将其在原集合中删除，避免内存泄漏
        if(!this.isLive){
            tf.bullets.remove(this);
        }

        /*Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, 5, 5);
        g.setColor(c);*/

        switch (dir){
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
            default:
                break;
        }
        move();
    }

    public void move() {
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
        // 当子弹移动到窗口外时进行子弹内存的回收
        if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT){
            isLive = false;
        }
        b.x = this.x;
        b.y = this.y;
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

    /**
     * 碰撞检测
     * @param tank
     */
    public void collideWith(Tank tank) {
        if(tank.getGroup() == this.group){
            return;
        }
       // Rectangle b = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        if(b.intersects(tank.getT())){
            this.die();
            tank.die();
            // 计算爆炸的位置
            int eX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
            int eY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
            tf.explodes.add(new Explode(eX, eY, tf));
        }
    }
    // 子弹回收
    private void die() {
        isLive = false;
    }
}
