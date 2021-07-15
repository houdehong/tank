package com.houdehong.tank;


public class Main {

    public static void main(String[] args) throws InterruptedException {
		TankFrame frame = new TankFrame();
		int tankCount = Integer.parseInt((String) PropertyMgr.getKey("initTankCount"));
		for(int i = 0; i < tankCount; i++){
            Tank tank = new Tank(100 + i * 80, 100, Dir.DOWN, frame, Group.BAD);
            tank.setMoving(true);
            frame.tanks.add(tank);
        }
		// new Thread(()->new Audio("audio/war1.wav").loop()).start();
		while (true){
            Thread.sleep(50);
            frame.repaint();
        }
    }
}
