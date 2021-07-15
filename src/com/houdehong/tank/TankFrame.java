package com.houdehong.tank;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author houdehong
 * @date 2021-07-06
 * @description
 */
public class TankFrame extends Frame {
     static final int GAME_WIDTH = 800;
     static final int GAME_HEIGHT = 600;
    Tank myTank = new Tank(300, 500, Dir.UP, this, Group.GOOD);
   // Bullet bullet = new Bullet(300, 300, Dir.DOWN);
    List<Bullet> bullets = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();
    // 敌方坦克
    List<Tank> tanks = new ArrayList<>();
    public TankFrame() {
        setVisible(true);
        // 设置大小
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setTitle("tank war");
        // 窗口是否可以改变大小
        setResizable(false);
        addKeyListener(new MyKeyListener() {

        });
        // 关闭窗口
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                System.exit(0);
            }
        });
    }

    // 自动调用
    @Override
    public void paint(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量：" + bullets.size(), 50,50);
        g.drawString("坦克的数量：" + tanks.size(), 50,80);
        g.drawString("爆炸的数量：" + explodes.size(), 50,100);
        g.setColor(c);
        // 将画笔交给tank自身
        myTank.paint(g);
        // 画出一连串的子弹
        for (int i = 0; i < bullets.size(); i++){
            bullets.get(i).paint(g);
        }

        // 画出敌方坦克
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }

        // 画出爆炸
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
        // 碰撞检测
        for(int i = 0; i < bullets.size(); i++){
            for(int j = 0; j < tanks.size(); j++){
                bullets.get(i).collideWith(tanks.get(j));
            }
        }

    }

    class MyKeyListener extends KeyAdapter {
        boolean bU = false;
        boolean bD = false;
        boolean bL = false;
        boolean bR = false;

        // 在健在按下的时候被调用
        @Override
        public void keyPressed(KeyEvent e) {
            // super.keyPressed(e);
            //x += 100;
            // 重画会调用paint()
            // repaint();

            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    //  x -= 10;
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    //  x+=10;
                    bR = true;
                    break;
                case KeyEvent.VK_UP:
                    // y -= 10;
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    // y += 10;
                    bD = true;
                    break;
                default:
                    break;
            }
            // 控制坦克可以移动
            myTank.setMoving(true);
            setMainDir();
        }

        // 在健被弹起来的时候调用
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    //  x -= 10;
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    //  x+=10;
                    bR = false;
                    break;
                case KeyEvent.VK_UP:
                    // y -= 10;
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    // y += 10;
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    // fire的时候打出子弹
                    myTank.fire();
                    break;
                default:
                    break;
            }
            setMainDir();
        }

        // 设置坦克的方向
        private void setMainDir() {
            if(!bR && !bL && !bU && !bD){
                myTank.setMoving(false);
            }else {
                if (bR) {
                    myTank.setDir(Dir.RIGHT);
                }
                if (bL) {
                    myTank.setDir(Dir.LEFT);
                }
                if (bU) {
                    myTank.setDir(Dir.UP);
                }
                if (bD) {
                    myTank.setDir(Dir.DOWN);
                }
            }
        }
    }

    // 解决闪烁：双缓冲，先画在内存里，再画在窗口里
    Image offScreeImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreeImage == null) {
            offScreeImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics imageGraphics = offScreeImage.getGraphics();
        Color c = imageGraphics.getColor();
        imageGraphics.setColor(Color.BLACK);
        imageGraphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        imageGraphics.setColor(c);
        paint(imageGraphics);
        g.drawImage(offScreeImage, 0, 0, null);
    }

}
