package sketch.template;
import constant.CURSOR;
import constant.SETTINGS;
import util.PVector;
import util.color;
import engine.Sketch;
import java.util.ArrayList;
import javafx.scene.image.Image;
import java.util.HashMap;
import javafx.scene.input.KeyCode;
public class __UserDefault extends Sketch {
public void setup() {
	size(1920, 1080);
	background(0, 0, 0, 255);
	textSize(50);
	fullScreen();
	//frameRate(1000);
}
color pink = color(0x0ffff2fC);
public void draw() {
	/*if(frameCount >= 2)
		noLoop();*/
	//if(frameCount%100 == 0) {
	      background(0);
		  text(frameRate, width/2, height/2);
	//}
	//stroke(255);/*
	//loadFastPixels();
	
	//putPixels((int)(frameCount%width * width) + (int)frameCount%width, pink);
	//updateFastPixels();
	//text(""+frameRate, width/2, height/2);
}
}