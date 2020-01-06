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
	size(500,500);
	background(0, 0, 0, 255);
	textSize(50);
	//fullScreen();
	frameRate(1000);
	//smooth(true);
	setResizable(true);
}
color pink = color(0x0ffff2fC);
int i = 0;
int j = 0;
public void draw() {
	//println("now has", width, height);
	//noLoop();
	//if(frameCount >= 1000)
		//noLoop();
	//if(frameCount%100 == 0) {
	      background(127);
		  //text(frameRate, width/2, height/2);
	//}
	stroke(255);
	noFill();
	
	//line(0, 0, width/2, height/2);
	text(""+frameRate, width/2, height/2);   
	loadFastPixels();
	int w;
	/*for(w = 0; w < 100; ++w) {
	//putPixels((j++)*width + (i++), pink);
		putPixels(((j +w)%height)*width + (i +w)%width, pink);
	}*/
	
	int step = 100;
	for(w = 0; w < step; ++w) {
		putPixels((i + w )%width, (j + w )%height, pink);
	}
	
	//rect(i-5, j-5, w+5, w+5);
	ellipse(0, 0, 20, 20);
	//println(width, height);
	++j;
	++i;
	
	//println(width, height, i, j);
	if(j >= height)
		j = 0;
	if(i >= width)
		i = 0;
	updateFastPixels();
	//line(i, j, i+step, j+step);
	/*println(minXChange,
			minYChange, maxXChange, maxYChange,
			maxXChange - minXChange,
			maxYChange - minXChange);*/
	//println(width, height);
	
}
}