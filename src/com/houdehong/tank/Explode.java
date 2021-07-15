package com.houdehong.tank;

import java.awt.*;

/**
 * @author houdehong
 * @date 2021-07-13
 * @description
 */
public class Explode {
    private int x;

    private int y;

    private TankFrame tf = null;
    // 控制爆炸图片
    private int step = 0;

    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    public Explode(int x, int y, TankFrame tf){
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    public void paint(Graphics graphics){
        // 计算坦克爆炸的范围
        graphics.drawImage(ResourceMgr.explodes[step++], this.x, this.y, null);
        if(step >= ResourceMgr.explodes.length){
            // 爆炸之后移除爆炸
            tf.explodes.remove(this);
        }
    }


}
