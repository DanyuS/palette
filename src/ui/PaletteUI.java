package ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import service.OperationService;
import serviceImpl.OperationServiceImpl;
 
public class PaletteUI extends Frame{
    private static final long serialVersionUID = 1L;
    MouseEvent e = null;
    int startX = -1;
    int startY = -1;
    int endX = -1;
    int endY = -1;
    boolean left = false;
    static int count=0;
    
    private static String shapename="";
    
    static OperationService operationService=new OperationServiceImpl();
    
    public PaletteUI(String title){
        setTitle (title);
        setLayout (new BorderLayout ());
        setResizable (false);
        setSize (1000, 800);
        setLocationRelativeTo (null);
        addWindowListener(new WindowAdapter (){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
 
    @Override
    public void paintComponents(Graphics g){
        float lineWidth = 4.0f;
        ( (Graphics2D) g ).setStroke(new BasicStroke(lineWidth));
        g.setColor(Color.BLACK);
        g.drawLine(startX, startY, endX, endY);
        g.dispose();
    }
 
    //形状label字体大小及位置设定
    public static void setShape(Graphics g,String shape,int x,int y) {
		g.setColor(Color.red);
		g.setFont(new Font("Tahoma", Font.BOLD, 30));
		g.drawString(shape, x, y);
    }
    
    public static void main(String[] args){
        final PaletteUI mdp = new PaletteUI ("Palette");
        
        Panel panel = new Panel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Label startL = new Label ("start: ");
        Label endL = new Label ("end: ");
        final Label nowL = new Label ("now: ");
        final Label startR = new Label ("000,000");
        final Label endR = new Label ("000,000");
        final Label nowN = new Label ("000,000");
        panel.add (startL);
        panel.add (startR);
        panel.add (endL);
        panel.add (endR);
        panel.add (nowL);
        panel.add (nowN);
        
        Panel panel1 = new Panel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        MenuBar mb = new MenuBar();
        mdp.setMenuBar(mb);
        Menu Open = new Menu("Open");
        mb.add(Open);
        
        
        PopupMenu openmenu=new PopupMenu();  
        
        ArrayList<String> fileList=new ArrayList<String>();
        fileList=operationService.readFileList();
        
        for(int i=0;i<fileList.size();i++) {
        	openmenu.add(fileList.get(i));
        }
        
        Open.add(openmenu);
        
        openmenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String cmd = e.getActionCommand();
				System.out.println(cmd);
				JFrame frame1 = new JFrame(cmd);
				frame1.setSize(1000, 800);
				ImageIcon picture=new ImageIcon("E:\\大三\\palette\\"+cmd);
				JLabel l=new JLabel();
				l.setIcon(picture);
				frame1.add(l);
				frame1.setVisible(true);
				//Dialog dialog=new Dialog(frame1,"对话框",false);
				frame1.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
				
			}
			
        });
        
        
        Button bt1=new Button("save");
        Button bt2=new Button("clear");
        Button bt3=new Button("recognize");
        Button bt4=new Button("standardize");
        panel1.add(bt1);
        panel1.add(bt2);
        panel1.add(bt3);
        panel1.add(bt4);
        
        bt1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//保存整个画板
				Date now = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
				String present = dateFormat.format(now);
				operationService.savePicture(present,mdp.getX(), mdp.getY(), mdp.getWidth(), mdp.getHeight());
				openmenu.add(present+".jpg");
				mdp.repaint();
			}
        });
        
        
        bt2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//清空整个画板
				mdp.repaint();
				operationService.clearMap();
				count=0;
			}
        });
        
        
        bt3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int num=countPaintNum();
				boolean squareOrRect=operationService.confirmScale();
				if(num==1) {
					//System.out.println("The shape is Circle");
					Graphics g = mdp.getGraphics();
					setShape(g, "Circle", mdp.startX, mdp.startY);
					shapename="Circle";
					/*g.setColor(Color.red);
					g.setFont(new Font("Tahoma", Font.BOLD, 30));
					g.drawString("Circle", mdp.startX, mdp.startY);
					System.out.println("Label's location is :"+mdp.startX+mdp.startY);*/
					count=0;
				}else if(num==2||num==3) {
					Graphics g = mdp.getGraphics();
					setShape(g, "Triangle", mdp.startX, mdp.startY);
					shapename="Triangle";
					count=0;
				}else if(num>4){
					Graphics g = mdp.getGraphics();
					String shapes=String.valueOf(num)+"tangle";
					setShape(g, shapes, mdp.startX, mdp.startY);
					shapename=shapes;
					count=0;
					//"Sorry,I don't know!"
				}else if(squareOrRect==false) {
					Graphics g = mdp.getGraphics();
					setShape(g, "Square", mdp.startX, mdp.startY);
					shapename="Square";
					count=0;
				}else if(squareOrRect==true) {
					Graphics g = mdp.getGraphics();
					setShape(g, "Rectangle", mdp.startX, mdp.startY);
					shapename="Rectangle";
					count=0;
				}
			}
        });
        
        bt4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//将图形规范化
				if(shapename.equals("Circle")) {
					Graphics g = mdp.getGraphics();
					operationService.standardizeCircle(g);
				}else if(shapename.equals("Triangle")) {
					Graphics g = mdp.getGraphics();
					operationService.standardizeTriangle(g);
				}else if(shapename.equals("Square")) {
					Graphics g = mdp.getGraphics();
					operationService.standardizeSquare(g);
				}else if(shapename.equals("Rectangle")) {
					Graphics g = mdp.getGraphics();
					operationService.standardizeRectangle(g);
				}else {
					Graphics g = mdp.getGraphics();
					operationService.standardizeOthers(g);
					setShape(g, "Fail to Standardize!", mdp.startX, mdp.startY+30);
				}
			}
        });
        
        
        mdp.add (panel, "South");
        mdp.add (panel1, "North");
        mdp.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseMoved(MouseEvent e){
                if (mdp.left){
                    nowN.setText (e.getX () + " , " + e.getY ());//move without pressed
                }
            }
 
            @Override
            public void mouseDragged ( MouseEvent e ){
            	Map<Integer,Integer> point=new HashMap<Integer,Integer>(); 
                if (mdp.left){
                    mdp.endX = e.getX ();
                    mdp.endY = e.getY ();
                    mdp.paintComponents (mdp.getGraphics ());
                    mdp.startX = mdp.endX;
                    mdp.startY = mdp.endY;
                    endR.setText (mdp.endX + " , " + mdp.endY);
                    System.out.println( e.getX ()+","+e.getY ());//move when dragged,save it 
                    point.put(e.getX (), e.getY ()); 
                }
                operationService.transPoint(point);
            }
        });
        mdp.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if (e.getButton () == MouseEvent.BUTTON1){
                    mdp.startX = e.getX ();
                    mdp.startY = e.getY ();
                    startR.setText (mdp.startX + " , " + mdp.startY);//start place
                    mdp.left = true;
                }
                else
                {
                    mdp.left = false;
                }
            }
 
            @Override
            public void mouseReleased(MouseEvent e){
                if (mdp.left)
                {
                    endR.setText (e.getX () + " , " + e.getY ());//end place
                    countPaintNum();
                }
            }
        });
        mdp.setVisible (true);
    }
    
    public static int countPaintNum() {
    	return count++;	
    }
}