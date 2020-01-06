package engine;

import constant.CURSOR;
import constant.SETTINGS;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import sketch.template.__UserDefault;
//import engine.Time;
//import engine.Sketch;
import util.FileManager;

public class Core extends Application {
	public static int width = 800;
	public static int height = 600;
	public static long frameCount = -1;
	public double frameRate = 0; 
	private float targetFrameRate = 60f;
	private Canvas canvas;
	protected GraphicsContext context;
	static private Stage mainStage;
	private Group root;
	private Scene scene;
	protected Sketch sketch; 
	protected CURSOR cursor = CURSOR.ARROW;
	static String dirPath = FileManager.path("sketchBooks","project1");
	Window window;
	Rectangle2D bound;
    public static void main(String[] args) {
        /*Application.launch(Core.class, args);*/
    	System.out.println("Test");
    	//new SketchTemplate(FileManager.listFile(dirPath,"pjfx"), dirPath);
    	Application.launch(Core.class, args);
    }
     
    protected void update(double elapsedTime){
    	setTargetFrameRate(sketch.getTargetFrameRate());
    	sketch.deltaTime = (float)elapsedTime/1000.0f;
    	sketch.deltaTimeMillis = (float)elapsedTime;
    	if(cursor != sketch.cursor);
    		setCursor(sketch.cursor);
		mainStage.setResizable(sketch.resizable);
		//sketch.width = (short) scene.getWidth();
		//sketch.height = (short) scene.getHeight();
		//System.out.println(""+ canvas.getWidth() + " " + canvas.getHeight());
    	if(sketch.isLoop) {
    		sketch.pushMatrix();
    		sketch.draw();
    		sketch.popMatrix();
    		//sketch.translate(0, 0);
    	}
    	//sketch.println(width, height, sketch.width, sketch.height);
    	sketch.frameRate = (float) frameRate;
    	++sketch.frameCount;
    	if(sketch.finished == true) {
    		Platform.exit();
    	}
    	
    }
    
    private void setCursor(CURSOR c) {
    	cursor = c;
    	switch(cursor) {
    	case CROSS:
    		scene.setCursor(Cursor.CROSSHAIR);
    		break;
    	case HAND:
    		scene.setCursor(Cursor.CLOSED_HAND);
    		break;
    	case MOVE:
    		scene.setCursor(Cursor.MOVE);
    		break;
    	case TEXT:
    		scene.setCursor(Cursor.TEXT);
    		break;
    	case WAIT:
    		scene.setCursor(Cursor.WAIT);
    		break;
    	case ARROW:
    	default:
    		scene.setCursor(Cursor.DEFAULT);
    		break;
    	
    	}
    }

    static void setFullScreen() {
    	Rectangle2D bound = Screen.getPrimary().getVisualBounds();
    	width = (int)bound.getWidth();
    	height = (int)bound.getHeight();
    	mainStage.setFullScreen(true);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	mainStage = primaryStage;
    	//primaryStage.setResizable(false);
    	mainStage.setTitle("Hello World");
    	root = new Group();
    	scene = new Scene(root);
    	
    	mainStage.setScene(scene);
    	mainStage.setFullScreenExitHint("");
    	canvas = new Canvas(200, 200);
    	scene.setFill(Color.LIGHTGRAY);
    	sketch = new __UserDefault();
    	sketch.setContext(canvas);
    	
    	sketch.fill(255);
    	sketch.stroke(0);
    	sketch.colorMode(SETTINGS.RGB, 255, 255, 255, 255);
    	bound = Screen.getPrimary().getVisualBounds();
    	sketch.maxWidth = (int)bound.getWidth();
    	sketch.Ppixels = new int[(int)bound.getWidth() * (int)bound.getHeight()];
    	sketch.setup();
    	
    	
    	
    	
    	//scene.
    	//mainStage.sizeToScene();
    	//mainStage.setHeight(sketch.height+35);
    	//mainStage.setWidth(sketch.width+17);

    	
    	//MouseEvent
    	scene.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	
    	        switch(mouseEvent.getButton()) {
    	        case PRIMARY:
    	        	sketch.mouseButton = SETTINGS.LEFT;
    	        	break;
    	        case MIDDLE:
    	        	sketch.mouseButton = SETTINGS.CENTER;
    	        	break;
    	        case SECONDARY:
    	        	sketch.mouseButton = SETTINGS.RIGHT;
    	        	break;
    	        case FORWARD:
    	        	sketch.mouseButton = SETTINGS.NEXT;
    	        	break;
    	        case BACK:
    	        	sketch.mouseButton = SETTINGS.BACK;
    	        	break;
    	        default:
    	        	sketch.mouseButton = SETTINGS.NONE;
    	        	break;
    	        }
    	        
    	        
    	    	sketch.mousePressed();
    	    }
    	});
    	
    	scene.addEventHandler(MouseEvent.MOUSE_RELEASED  , new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	
    	    	sketch.mouseReleased();
    	    	sketch.mouseButton = SETTINGS.NONE;
    	    }
    	});
    	
    	scene.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>( ) {
    		@Override
    		public void handle(MouseEvent mouseEvent) {
    			sketch.mouseX =(float) mouseEvent.getX();
    			sketch.mouseY =(float) mouseEvent.getY();
    		}
    	});
    	
    	scene.addEventHandler(MouseEvent.MOUSE_DRAGGED , new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	        
    	    	sketch.mouseX = (float)mouseEvent.getX();
    			sketch.mouseY = (float)mouseEvent.getY();
    	    	sketch.mouseDragged();
    	    }
    	});
    	
    	
    	
    	//KeyEvent
    	scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				//get The keyPressed

				String tmp = event.getText();
				sketch.key = tmp.length() > 0 ? tmp : "CODED";
				sketch.keyCode = event.getCode();
				sketch.keyPressed();
			}
    		
    	});
    	
    	scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				String tmp = event.getText();
				sketch.key = tmp.length() > 0 ? tmp : "CODED";
				sketch.keyCode = event.getCode();
				sketch.keyReleased();
				//set KeyPressed to 0
				//set keyCode to 0
			}
    		
    	});
    	//if(sketch.resizable) {
	    	mainStage.widthProperty().addListener((obs, oldVal, newVal) -> {
	    	     // Do whatever you want
	    		
	    		resizeX(scene.getWidth());
	    	});
	
	    	mainStage.heightProperty().addListener((obs, oldVal, newVal) -> {
	    	     // Do whatever you want
	    		
	    		resizeY(scene.getHeight());
	    	});
    	//}
    	
    	
    	
    	
    	////////////
    	//canvas.setWidth(sketch.width);
    	//canvas.setHeight(sketch.height);
    	root.getChildren().add(canvas);
    	
    	sketch.setContext(canvas);
    	
    	mainStage.show();
    	window = Window.getWindows().get(0);
    	/*sketch.windowSizeX = window.getWidth();
    	sketch.windowSizeY = window.getHeight();
    	sketch.contentSizeX =scene.getWidth();
    	sketch.contentSizeY = scene.getHeight();*/
    	new Time(this).start();
    	/*sketch.width = (short)scene.getWidth();
    	sketch.height =  (short)scene.getHeight();*/
    	
    }
    
    protected void resizeX(double newVal) {
    	sketch.size((int)newVal, sketch.height);
    }
    
    protected void resizeY(double newVal) {
    	sketch.size(sketch.width,(int) newVal);
    }
    
    protected float getTargetFrameRate() {
		return targetFrameRate;
	}

	public void setTargetFrameRate(float targetFrameRate) {
		this.targetFrameRate = targetFrameRate;
	}
	
}
