package me.cmilby;

import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Panel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	
	private double[][] points;
	private List<Point> pointList;
	
	public Panel() {
		setFocusable(true);
		requestFocus();
		addKeyListener(this);

		points = new double[9][9];
		pointList = new ArrayList<Point>();
		pointList.add(new Point(0, 300));
		pointList.add(new Point(600, 300));
		
		for (int i = 0; i < points.length; i++)
			for (int j = 0; j < points[i].length; j++)
				points[i][j] = Double.MAX_VALUE;
		generate1DTerrain(pointList, 256, 256);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawTerrain(pointList, g);
	}

	public void generate1DTerrain(List<Point> list, int roughness, int npoints) {
		if (npoints <= 0) 
			return;
		for (int i = 1; i < list.size(); i += 2) {
			Point middle = middle(list.get(i).x, list.get(i).y, list.get(i - 1).x, list.get(i - 1).y);
			Point displace = displace(middle, roughness);
			list.add(i, displace);
			npoints--;
		}
		generate1DTerrain(list, roughness / 2, npoints);
	}

	public void generate2DTerrain(double[][] points, int roughness) {
		// For each iteration
		for (int i = 0; i < log(points.length, 2) * 2; i++) {

		}
	}

	public void drawTerrain(List<Point> points, Graphics g) {
		for (int i = 1; i < points.size(); i++)
			g.drawLine(points.get(i).x, points.get(i).y, points.get(i - 1).x, points.get(i - 1).y);
	}

	public void drawTerrain(double[][] points, Graphics g) {

	}

	public Point middle(int x1, int y1, int x2, int y2) {
		return new Point((x1 + x2) / 2, (y1 + y2) / 2);
	}

	public Point displace(Point p, int roughness) {
		return new Point(p.x, getRandomNumber(p.y - roughness, p.y + roughness));
	}

	public int getRandomNumber(int min, int max) {
		return (int)(Math.random() * (max - min + 1) + min);
	}

	public int log(int x, int base) {
    	return (int) (Math.log(x) / Math.log(base));
	}

	public void projectPoint(double x1, double y1, double x2, double y2, int size, Graphics g) {
   		g.drawLine((int) (x1 + (y1 / 2.0)), (int) (y1 - (size / 2.0)), (int) (x2 + (y2 / 2.0)), (int) (y2 - (size / 2.0)));
   	}

	public List<Point> getMiddlePoint(double[][] points) {
		List<Point> occur = new ArrayList<Point>();
		for (int i = 0; i < points.length; i++)
			for (int j = 0; j < points[i].length; j++)
				if (points[i][j] != Double.MAX_VALUE)
					occur.add(new Point(i, j));
		return occur;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// points.clear();
		// points.add(new Point(0, 300));
		// points.add(new Point(600, 300));
		// generate1DTerrain(points, 100, 127);
		pointList.clear();
		pointList.add(new Point(0, 300));
		pointList.add(new Point(600, 300));
		generate1DTerrain(pointList, 256, 256);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
