package sketch.template;
import constant.CURSOR;
import constant.SETTINGS;
import util.PVector;
import util.color;
import util.PShape;
import engine.Sketch;
import java.util.ArrayList;
import javafx.scene.image.Image;
import java.util.HashMap;
import javafx.scene.input.KeyCode;
import java.io.BufferedReader;
import engine.JSONObject;
import engine.JSONArray;
import engine.PImage;
public class __UserDefault extends Sketch{
int day, month, year;
float minL = 0.01f;
int secH = 50,minH = 50, hourH = 50;
float diam = min(width, height)/1.5f;
float offsetY = -50;
String[] days = {
		"Sunday","Monday","Tuesday","Wednesday",
		"Thursday","Friday","Saturday"
		
};
String[] months = {
		"January","February","March",
		"April","May","June",
		"July","August","September",
		"October","November","December"
};

public void setup() {
	size(500, 500);
	day = day();
	month = month();
	year = year();
	textAlign(CENTER,CENTER);
	textSize(30);
	strokeWeight(20);
	strokeCap(ROUND);
}

public void draw() {
	
	double sec = System.currentTimeMillis()/1000.0%60;
	diam = min(width, height)/1.5f;
	
	background(255);
	stroke(0, 0, 255);
	noFill();
	stroke(0, 0, 255);
	arc(width/2, height/2 +offsetY, diam-100,
			diam-100, -HALF_PI,map(hour()%12, 0, 12, 0, TAU)-HALF_PI-minL, OPEN);
	stroke(0, 255, 0);
	arc(width/2, height/2+offsetY, diam-50,
			diam-50, -HALF_PI,map(minute(), 0, 59, 0, TAU)-HALF_PI-minL, OPEN);
	stroke(255, 0, 0);
	arc(width/2, height/2+offsetY, diam,
			diam, -HALF_PI,map(sec, 0, 59, 0, TAU)-HALF_PI-minL, OPEN);
	
	fill(0);
	noStroke();
	int currentDay = (int)(System.currentTimeMillis()/1000/60/60/24 - 3)%7;
	text(days[currentDay] + ", " + months[month-1] + " " + day + getExt(day) + " " + year,width/2, 5*height/6);
	text(String.format("%02d",hour())+":"+
		String.format("%02d",minute())+":"+
		String.format("%02d",second()), width/2, height/2 + offsetY);
}

public String getExt(int i) {
	switch(i) {
	case 1:
	case 21:
	case 31:
		return "st";
	case 2:
	case 22:
		return "nd";
	case 3:
	case 23:
		return "rd";
	default:
		return "th";
	}
}
}