package i.gishreloaded.gishcode.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class RobotUtils {
	
	public static void clickMouse(int button){
		try {
			Robot bot = new Robot();
			if(button == 0) {
				bot.mousePress(InputEvent.BUTTON1_MASK);
				bot.mouseRelease(InputEvent.BUTTON1_MASK);
			} else if(button == 1) {
				bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
				bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			} else {
				return;
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public static void setMouse(int button, boolean state) {
		try {
			Robot bot = new Robot();
			if(button == 0) {
				if(state)
					bot.mousePress(InputEvent.BUTTON1_MASK);
				else
					bot.mouseRelease(InputEvent.BUTTON1_MASK);
			} else if(button == 1) {
				if(state)
					bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
				else
					bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			} else {
				return;
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
