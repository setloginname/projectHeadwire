package headwire;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Robot;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;

public class CodeWords {

	private String codeword;
	private String var;
	private String type;
	
	private static WebDriver driver;
	private static double multiplicator;

	public void execute (String codeword, String var, String type) throws Exception {
		this.codeword = codeword;
		this.var = var;
		this.type = type;

		switch(codeword) {
		case "use":
			use(var);
			break;
		case "open":
			open(var);
			break;
		case "click":
			click(var, type);
			break;
		case "goTo":
			goTo(var, type);
			break;
		case "input":
			input(var);
			break;
		case "rightclick":
			rightclick(var, type);
			break;
		case "dragndrop":
			dragndrop();
			break;
		case "audio":
			audio();
			break;
		case "upload":
			upload();
			break;
		}
	}

	private static void initChromeDriver() {
		String exePath = "C:\\Users\\Pascal\\Downloads\\chromedriver_win32\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		options.addArguments("--start-fullscreen");
		driver = new ChromeDriver(options);
	}
	
	private static void initFirefoxDriver() {
		String exePath = "C:\\Users\\Pascal\\Downloads\\geckodriver.exe";
		System.setProperty("webdriver.gecko.driver", exePath);
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--start-fullscreen");
		driver = new FirefoxDriver(options);
		
	}

	private static Robot initRobot() throws Exception {
		Robot robot = new Robot();
		return robot;
	}
	
	private static void setMultiplicator() {
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = g.getDefaultScreenDevice();
		long lDeviceResolution = device.getDisplayMode().getWidth();
		double deviceResolution = (double) lDeviceResolution;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long lBrowserResolution = (long) js.executeScript("return screen.width;");
		double browserResolution = (double) lBrowserResolution;
		multiplicator = deviceResolution/browserResolution;
	}

	private void upload() {
		// TODO Auto-generated method stub

	}

	private void audio() {
		// TODO Auto-generated method stub

	}

	private void dragndrop() {
		// TODO Auto-generated method stub

	}

	private void rightclick(String var, String type) {
		Actions oAction = new Actions(driver);
		if (type.equals("xpath")) {
			oAction.moveToElement(driver.findElement(By.xpath(var)));
			oAction.contextClick(driver.findElement(By.xpath(var))).build().perform();
		} else if (type.equals("id")) {
			oAction.moveToElement(driver.findElement(By.id(var)));
			oAction.contextClick(driver.findElement(By.id(var))).build().perform();
		} else if (type.equals("name")) {
			oAction.moveToElement(driver.findElement(By.name(var)));
			oAction.contextClick(driver.findElement(By.name(var))).build().perform();
		}		
	}

	private void input(String var) throws Exception {
		WebElement element = driver.switchTo().activeElement();
		element.sendKeys(var);
		Thread.sleep(1000);
//		element.submit();
	}

	private void goTo(String var, String type) throws Exception {
		Point pTo = null;
		if (type.equals("xpath")) {
			pTo = driver.findElement(By.xpath(var)).getLocation();
		} else if (type.equals("id")) {
			pTo = driver.findElement(By.id(var)).getLocation();
		} else if (type.equals("name")) {
			pTo = driver.findElement(By.name(var)).getLocation();
		}
		
		java.awt.Point mouse = MouseInfo.getPointerInfo().getLocation();
		
		double pFromX = mouse.x;
		double pFromY = mouse.y;
		
		setMultiplicator();

		double pToX = pTo.x*multiplicator;
		double pToY = pTo.y*multiplicator;

		Dimension toSize = null;
		if (type.equals("xpath")) {
			toSize = driver.findElement(By.xpath(var)).getSize();
		} else if (type.equals("id")) {
			toSize = driver.findElement(By.id(var)).getSize();
		} else if (type.equals("name")) {
			toSize = driver.findElement(By.name(var)).getSize();
		}

		int xCentreTo = toSize.width / 2;
		int yCentreTo = toSize.height / 2;

		pToX += xCentreTo;
		pToY += yCentreTo;

		int i = (int) pFromX;
		int j = (int) pFromY;

		if(pFromX < pToX && pFromY < pToY) {
			for(i = (int) pFromX; i<pToX; i++) {
				initRobot().mouseMove(i, j);
				Thread.sleep(4);
				if(j<pToY) {
					j++;
				}
			}
			
		} else if(pFromX < pToX && pFromY > pToY) {
			for(i = (int) pFromX; i<pToX; i++) {
				initRobot().mouseMove(i, j);
				Thread.sleep(4);
				if(j>pToY) {
					j--;
				}
			}

		} else if(pFromX > pToX && pFromY < pToY) {
			for(i = (int) pFromX; i>pToX; i--) {
				initRobot().mouseMove(i, j);
				Thread.sleep(4);
				if(j<pToY) {
					j++;
				}
			}

		} else if(pFromX > pToX && pFromY > pToY) {
			for(i = (int) pFromX; i>pToX; i--) {
				initRobot().mouseMove(i, j);
				Thread.sleep(4);
				if(j>pToY) {
					j--;
				}
			}
		}
	}

	private void click(String var, String type) throws Exception {
		if (type.equals("xpath")) {
			driver.findElement(By.xpath(var)).click();
		} else if (type.equals("id")) {
			driver.findElement(By.id(var)).click();
		} else if (type.equals("name")) {
			driver.findElement(By.name(var)).click();
		}	
		Thread.sleep(2000);
	}

	private void open(String var) {
		driver.get(var);

	}

	private void use(String var) {
		if(var.equals("chrome")) {
			initChromeDriver();
		} else if (var.equals("firefox")) {
			initFirefoxDriver();
		}
	}
	
	public void quit() {
		driver.quit();
	}
}

