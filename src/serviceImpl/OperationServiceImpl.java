package serviceImpl;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import service.OperationService;

public class OperationServiceImpl implements OperationService {

	Map<Integer,Integer> location=new HashMap<Integer,Integer>();
	@Override
	public ArrayList<String> readFileList() {
		// TODO Auto-generated method stub
		ArrayList<String> filename=new ArrayList<String>();
		File fileDir = new File("E:\\大三\\palette");
		File[] files = fileDir.listFiles();
		for (int i = 0; i < files.length; i++) {
        	String name = files[i].getName();
        	filename.add(name);
        }
		return filename;
	}

	@Override
	public void savePicture(String present,int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		String fn="E:\\大三\\palette\\"+present+".jpg";
		File file = new File(fn);
		
		BufferedImage myImage = null;
		try {
			myImage = new Robot().createScreenCapture(new Rectangle(x+10, y+90, w-10, h-130));
			ImageIO.write(myImage, "jpg", new File(fn));
		} catch (AWTException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			file.createNewFile();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}

	@Override
	public Map<Integer, Integer> transPoint(Map<Integer, Integer> point) {
		// TODO Auto-generated method stub
		Iterator it = point.entrySet().iterator();  
        while (it.hasNext()) {  
        	Map.Entry entry = (Map.Entry) it.next();  
        	Object key = entry.getKey();  
        	Object value = (Integer) entry.getValue();
        	int keys=Integer.parseInt(key.toString());
        	int values=Integer.parseInt(value.toString());
        	location.put(keys, values);
        	//System.out.println("key=" + keys + " value=" + values);  
        }
        getMaxPoint();
		return location;
	}
	
	@Override
	public Map<Integer, Integer> getMaxPoint() {
		// TODO Auto-generated method stub
		Map<Integer,Integer> result=new HashMap<Integer,Integer>();
		Set<Integer> set = location.keySet();
		Object[] obj = set.toArray();
		Arrays.sort(obj);
		System.out.println("right-key is"+Integer.parseInt(obj[obj.length - 1].toString())+" "+location.get(obj[obj.length - 1]));
		System.out.println("left-key is"+Integer.parseInt(obj[0].toString())+" "+location.get(obj[0]));
		
		Collection<Integer> c = location.values();
		Object[] obj1 = c.toArray();
		Arrays.sort(obj1);
		int dx=0;
		int ux=0;
		
		for(Map.Entry<Integer, Integer> entry:location.entrySet()) {
			if(obj1[obj1.length-1].equals(entry.getValue())){
				dx=entry.getKey();
			}
			if(obj1[0].equals(entry.getValue())){
				ux=entry.getKey();
			}
		}
		System.out.println("down-key is"+dx+" "+Integer.parseInt(obj1[obj1.length-1].toString()));
		System.out.println("up-key is"+ux+" "+Integer.parseInt(obj1[0].toString()));
		
		result.put(Integer.parseInt(obj[0].toString()), location.get(obj[0]));
		result.put(Integer.parseInt(obj[obj.length - 1].toString()), location.get(obj[obj.length - 1]));
		result.put(ux, Integer.parseInt(obj1[0].toString()));
		result.put(dx, Integer.parseInt(obj1[obj1.length-1].toString()));
		//左右上下
		
		return result;
	}

	@Override
	public void clearMap() {
		// TODO Auto-generated method stub
		location.clear();
	}

	@Override
	public void standardizeRectangle(Graphics g) {
		// TODO Auto-generated method stub
		Map<Integer,Integer> rect=getMaxPoint();
		Map<Integer, Integer> mapKey = new HashMap<Integer, Integer>();
        Map<Integer, Integer> mapValue = new HashMap<Integer, Integer>();
        int i = rect.size()-1;
        for(Entry<Integer, Integer> entry : rect.entrySet()) {
        	mapKey.put(i, entry.getKey());
            mapValue.put(i, entry.getValue());
            i--;
        }
         
        Collection<Integer> c = mapKey.values();
		Object[] obj = c.toArray();
		Arrays.sort(obj);
		int dx=0;
		int ux=0;
		
		for(Map.Entry<Integer, Integer> entry:mapKey.entrySet()) {
			if(obj[obj.length-1].equals(entry.getValue())){
				dx=entry.getKey();
			}
			if(obj[0].equals(entry.getValue())){
				ux=entry.getKey();
			}
		}
		
		
		Collection<Integer> c1 = mapValue.values();
		Object[] obj1 = c1.toArray();
		Arrays.sort(obj1);
		int dx1=0;
		int ux1=0;
		
		for(Map.Entry<Integer, Integer> entry:mapValue.entrySet()) {
			if(obj1[obj1.length-1].equals(entry.getValue())){
				dx1=entry.getKey();
			}
			if(obj1[0].equals(entry.getValue())){
				ux1=entry.getKey();
			}
		}
		//Graphics g = mdp.getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		float lineWidth = 4.0f;
        ( (Graphics2D) g ).setStroke(new BasicStroke(lineWidth));
		g2.setColor(Color.RED);
		int h=mapValue.get(dx1)-mapValue.get(ux1);
		int w=mapKey.get(dx)-mapKey.get(ux);
		g2.drawRect(mapKey.get(ux),mapValue.get(ux1),w,h);//x,y起点坐标，w宽，h高
		clearMap();
		
	}

	@Override
	public void standardizeSquare(Graphics g) {
		// TODO Auto-generated method stub
		Map<Integer,Integer> squa=getMaxPoint();
		Map<Integer, Integer> mapKey = new HashMap<Integer, Integer>();
        Map<Integer, Integer> mapValue = new HashMap<Integer, Integer>();
        int i = squa.size()-1;
        for(Entry<Integer, Integer> entry : squa.entrySet()) {
        	mapKey.put(i, entry.getKey());
            mapValue.put(i, entry.getValue());
            i--;
        }
         
        Collection<Integer> c = mapKey.values();
		Object[] obj = c.toArray();
		Arrays.sort(obj);
		int dx=0;
		int ux=0;
		
		for(Map.Entry<Integer, Integer> entry:mapKey.entrySet()) {
			if(obj[obj.length-1].equals(entry.getValue())){
				dx=entry.getKey();
			}
			if(obj[0].equals(entry.getValue())){
				ux=entry.getKey();
			}
		}
		
		
		Collection<Integer> c1 = mapValue.values();
		Object[] obj1 = c1.toArray();
		Arrays.sort(obj1);
		int dx1=0;
		int ux1=0;
		
		for(Map.Entry<Integer, Integer> entry:mapValue.entrySet()) {
			if(obj1[obj1.length-1].equals(entry.getValue())){
				dx1=entry.getKey();
			}
			if(obj1[0].equals(entry.getValue())){
				ux1=entry.getKey();
			}
		}
		//Graphics g = mdp.getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		float lineWidth = 4.0f;
        ( (Graphics2D) g ).setStroke(new BasicStroke(lineWidth));
		g2.setColor(Color.RED);
		int h=mapValue.get(dx1)-mapValue.get(ux1);
		int w=mapKey.get(dx)-mapKey.get(ux);
		int a=0;
		if(h>w) {
			a=h;
		}else {
			a=w;
		}
		g2.drawRect(mapKey.get(ux),mapValue.get(ux1),a,a);//x,y起点坐标，w宽，h高
		clearMap();
	}

	@Override
	public void standardizeTriangle(Graphics g) {
		// TODO Auto-generated method stub
		Map<Integer,Integer> squa=getMaxPoint();
		Map<Integer, Integer> mapKey = new HashMap<Integer, Integer>();
        Map<Integer, Integer> mapValue = new HashMap<Integer, Integer>();
        int i = squa.size()-1;
        for(Entry<Integer, Integer> entry : squa.entrySet()) {
        	mapKey.put(i, entry.getKey());
            mapValue.put(i, entry.getValue());
            i--;
        }
         
        Collection<Integer> c = mapKey.values();
		Object[] obj = c.toArray();
		Arrays.sort(obj);
		int dx=0;
		int ux=0;
		
		for(Map.Entry<Integer, Integer> entry:mapKey.entrySet()) {
			if(obj[obj.length-1].equals(entry.getValue())){
				dx=entry.getKey();
			}
			if(obj[0].equals(entry.getValue())){
				ux=entry.getKey();
			}
		}
		
		
		Collection<Integer> c1 = mapValue.values();
		Object[] obj1 = c1.toArray();
		Arrays.sort(obj1);
		int dx1=0;
		int ux1=0;
		
		for(Map.Entry<Integer, Integer> entry:mapValue.entrySet()) {
			if(obj1[obj1.length-1].equals(entry.getValue())){
				dx1=entry.getKey();
			}
			if(obj1[0].equals(entry.getValue())){
				ux1=entry.getKey();
			}
		}
		Graphics2D g2 = (Graphics2D)g;
		float lineWidth = 4.0f;
        ( (Graphics2D) g ).setStroke(new BasicStroke(lineWidth));
		g2.setColor(Color.RED);
		
		g2.drawLine(mapKey.get(ux), mapValue.get(ux1), mapKey.get(ux), mapValue.get(dx1));//up
		g2.drawLine(mapKey.get(ux), mapValue.get(ux1), mapKey.get(dx), mapValue.get(dx1));//left
		g2.drawLine(mapKey.get(ux), mapValue.get(dx1), mapKey.get(dx), mapValue.get(dx1));//right
		clearMap();
	}

	@Override
	public void standardizeCircle(Graphics g) {
		// TODO Auto-generated method stub
		Map<Integer,Integer> squa=getMaxPoint();
		Map<Integer, Integer> mapKey = new HashMap<Integer, Integer>();
        Map<Integer, Integer> mapValue = new HashMap<Integer, Integer>();
        int i = squa.size()-1;
        for(Entry<Integer, Integer> entry : squa.entrySet()) {
        	mapKey.put(i, entry.getKey());
            mapValue.put(i, entry.getValue());
            i--;
        }
         
        Collection<Integer> c = mapKey.values();
		Object[] obj = c.toArray();
		Arrays.sort(obj);
		int dx=0;
		int ux=0;
		
		for(Map.Entry<Integer, Integer> entry:mapKey.entrySet()) {
			if(obj[obj.length-1].equals(entry.getValue())){
				dx=entry.getKey();
			}
			if(obj[0].equals(entry.getValue())){
				ux=entry.getKey();
			}
		}
		
		
		Collection<Integer> c1 = mapValue.values();
		Object[] obj1 = c1.toArray();
		Arrays.sort(obj1);
		int dx1=0;
		int ux1=0;
		
		for(Map.Entry<Integer, Integer> entry:mapValue.entrySet()) {
			if(obj1[obj1.length-1].equals(entry.getValue())){
				dx1=entry.getKey();
			}
			if(obj1[0].equals(entry.getValue())){
				ux1=entry.getKey();
			}
		}
		Graphics2D g2 = (Graphics2D)g;
		float lineWidth = 4.0f;
        ( (Graphics2D) g ).setStroke(new BasicStroke(lineWidth));
		g2.setColor(Color.RED);
		int h=mapValue.get(dx1)-mapValue.get(ux1);
		int w=mapKey.get(dx)-mapKey.get(ux);
		g2.drawOval(mapKey.get(ux), mapValue.get(ux1), w, h);
		clearMap();
	}

	@Override
	public void standardizeOthers(Graphics g) {
		// TODO Auto-generated method stub
		clearMap();
		
	}

	@Override
	public boolean confirmScale() {
		Map<Integer,Integer> squa=getMaxPoint();
		Map<Integer, Integer> mapKey = new HashMap<Integer, Integer>();
        Map<Integer, Integer> mapValue = new HashMap<Integer, Integer>();
        int i = squa.size()-1;
        for(Entry<Integer, Integer> entry : squa.entrySet()) {
        	mapKey.put(i, entry.getKey());
            mapValue.put(i, entry.getValue());
            i--;
        }
         
        Collection<Integer> c = mapKey.values();
		Object[] obj = c.toArray();
		Arrays.sort(obj);
		int dx=0;
		int ux=0;
		
		for(Map.Entry<Integer, Integer> entry:mapKey.entrySet()) {
			if(obj[obj.length-1].equals(entry.getValue())){
				dx=entry.getKey();
			}
			if(obj[0].equals(entry.getValue())){
				ux=entry.getKey();
			}
		}
		
		
		Collection<Integer> c1 = mapValue.values();
		Object[] obj1 = c1.toArray();
		Arrays.sort(obj1);
		int dx1=0;
		int ux1=0;
		
		for(Map.Entry<Integer, Integer> entry:mapValue.entrySet()) {
			if(obj1[obj1.length-1].equals(entry.getValue())){
				dx1=entry.getKey();
			}
			if(obj1[0].equals(entry.getValue())){
				ux1=entry.getKey();
			}
		}
		
		int h=mapValue.get(dx1)-mapValue.get(ux1);
		int w=mapKey.get(dx)-mapKey.get(ux);
		
		boolean result=true;
		
		if(h/w>1.2||h/w<0.83) {
			result=true;//rectangle
		}else {
			result=false;
		}	
		return result;
	}
}