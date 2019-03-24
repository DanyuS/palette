package service;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Map;

public interface OperationService {
	public ArrayList<String> readFileList();
	public void savePicture(String present,int x,int y,int w,int h);
	public Map<Integer, Integer> transPoint(Map<Integer, Integer> point);
	public Map<Integer, Integer> getMaxPoint();
	public void clearMap();
	
	public void standardizeRectangle(Graphics g);
	public void standardizeSquare(Graphics g);
	public void standardizeTriangle(Graphics g);
	public void standardizeCircle(Graphics g);
	public void standardizeOthers(Graphics g);
	
	public boolean confirmScale();
}
