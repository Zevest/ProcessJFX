package engine;

//import util.Math;
import constant.MOUSE_BUTTON;

//import engine.Time;
//import engine.Sketch;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sketch.example1.UserSketch;


public class Core extends Application {
	public static int width = 800;
	public static int height = 600;
	public static long frameCount = -1;
	public double frameRate = 0; 
	private float targetFrameRate = 60f;
	private Canvas canvas;
	protected GraphicsContext context;
	private Stage mainStage;
	private Group root;
	private Scene scene;
	protected Sketch sketch; 

	
	
    public static void main(String[] args) {
        Application.launch(Core.class, args);
    }
    
    
    protected void update(){
    	setTargetFrameRate(sketch.getTargetFrameRate());
    	if(sketch.isLoop) {
    		sketch.draw();
    		sketch.translate(0, 0);
    	}
    	sketch.frameRate = (float) frameRate;
    	++sketch.frameCount;
    	
    }

    
    @Override
    public void start(Stage primaryStage) {
    	mainStage = primaryStage;
    	mainStage.setTitle("Hello World");
    	root = new Group();
    	scene = new Scene(root);
    	
    	mainStage.setScene(scene);
    	
    	canvas = new Canvas(200, 200);
    	scene.setFill(Color.LIGHTGRAY);
    	sketch = new UserSketch();
    	sketch.setContext(canvas);
    	sketch.background(150);
    	sketch.fill(255);
    	sketch.setup();
    	
    	
    	//MouseEvent
    	
    	scene.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	        switch(mouseEvent.getButton()) {
    	        case PRIMARY:
    	        	sketch.mouseButton = MOUSE_BUTTON.LEFT;
    	        	break;
    	        case MIDDLE:
    	        	sketch.mouseButton = MOUSE_BUTTON.CENTER;
    	        	break;
    	        case SECONDARY:
    	        	sketch.mouseButton = MOUSE_BUTTON.RIGHT;
    	        	break;
    	        case FORWARD:
    	        	sketch.mouseButton = MOUSE_BUTTON.NEXT;
    	        	break;
    	        case BACK:
    	        	sketch.mouseButton = MOUSE_BUTTON.BACK;
    	        	break;
    	        default:
    	        	sketch.mouseButton = MOUSE_BUTTON.NONE;
    	        	break;
    	        }
    	        
    	        
    	    	sketch.mousePressed();
    	    }
    	});
    	
    	scene.addEventHandler(MouseEvent.MOUSE_RELEASED  , new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	
    	    	sketch.mouseReleased();
    	    	sketch.mouseButton = MOUSE_BUTTON.NONE;
    	    }
    	});
    	
    	scene.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>( ) {
    		@Override
    		public void handle(MouseEvent mouseEvent) {
    			sketch.mouseX = mouseEvent.getX();
    			sketch.mouseY = mouseEvent.getY();
    		}
    	});
    	
    	scene.addEventHandler(MouseEvent.MOUSE_DRAGGED , new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	        
    	    	sketch.mouseX = mouseEvent.getX();
    			sketch.mouseY = mouseEvent.getY();
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
    	
    	
    	////////////
    	canvas.setWidth(sketch.width);
    	canvas.setHeight(sketch.height);
    	root.getChildren().add(canvas);
    	
    	sketch.setContext(canvas);
    	
    	new Time(this).start();
    	mainStage.show();
    	
    }
    
    protected float getTargetFrameRate() {
		return targetFrameRate;
	}

	public void setTargetFrameRate(float targetFrameRate) {
		this.targetFrameRate = targetFrameRate;
	}
	
}
