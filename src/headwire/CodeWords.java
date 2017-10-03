package headwire;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
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
	private static ScreenRecorder screenRecorder;

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
		case "dragAndDrop":
			dragAndDdrop();
			break;
		case "audio":
			audio();
			break;
		case "upload":
			upload();
			break;
		case "doubleclick":
			doubleclick(var, type);
			break;
		case "goToAndClick":
			goToAndClick(var, type);
			break;
		case "highlight":
			highlight(var, type);
			break;
		case "scrolldown":
			scrolldown();
			break;
		case "scrollup":
			scrollup();
			break;
		case "enter":
			enter();
			break;
		case "start":
			start();
			break;
		case "stop":
			stop();
			break;
		case "quit":
			quit();
			break;

		}
	}
	private void quit() {
		driver.quit();
		
	}
	private void enter() {
		WebElement element = driver.switchTo().activeElement();
		element.submit();		
	}

	private void stop() throws Exception {
		screenRecorder.stop();
	}

	private void start() throws Exception {
		initScreenRecorder();
		screenRecorder.start();
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

	private static void initScreenRecorder() throws Exception {
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice()
				.getDefaultConfiguration();

		screenRecorder = new ScreenRecorder(gc,
				new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
						CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
						DepthKey, (int)24, FrameRateKey, Rational.valueOf(15),
						QualityKey, 1.0f,
						KeyFrameIntervalKey, (int) (15 * 60)),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,"black",
						FrameRateKey, Rational.valueOf(30)),
				null);
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

	private void dragAndDdrop() throws Exception {
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
		
		initRobot().mousePress(InputEvent.BUTTON1_MASK);

		if(pFromX < pToX && pFromY < pToY) {
			for(i = (int) pFromX; i<pToX; i++) {
				initRobot().mouseMove(i, j);
				Thread.sleep(2);
				if(j<pToY) {
					j++;
				}
			}

		} else if(pFromX < pToX && pFromY > pToY) {
			for(i = (int) pFromX; i<pToX; i++) {
				initRobot().mouseMove(i, j);
				Thread.sleep(2);
				if(j>pToY) {
					j--;
				}
			}

		} else if(pFromX > pToX && pFromY < pToY) {
			for(i = (int) pFromX; i>pToX; i--) {
				initRobot().mouseMove(i, j);
				Thread.sleep(2);
				if(j<pToY) {
					j++;
				}
			}

		} else if(pFromX > pToX && pFromY > pToY) {
			for(i = (int) pFromX; i>pToX; i--) {
				initRobot().mouseMove(i, j);
				Thread.sleep(2);
				if(j>pToY) {
					j--;
				}
			}
		}
		
		initRobot().mouseRelease(InputEvent.BUTTON1_MASK);
	}

	private void scrolldown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0, 250);");
	}
	
	private void scrollup() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0, -250);");
	}

	private void rightclick(String var, String type) {
		Actions action = new Actions(driver);
		if (type.equals("xpath")) {
			action.moveToElement(driver.findElement(By.xpath(var)));
			action.contextClick(driver.findElement(By.xpath(var))).build().perform();
		} else if (type.equals("id")) {
			action.moveToElement(driver.findElement(By.id(var)));
			action.contextClick(driver.findElement(By.id(var))).build().perform();
		} else if (type.equals("name")) {
			action.moveToElement(driver.findElement(By.name(var)));
			action.contextClick(driver.findElement(By.name(var))).build().perform();
		}		
	}

	private void input(String var) throws Exception {
		WebElement element = driver.switchTo().activeElement();
		element.sendKeys(var);
		Thread.sleep(1000);
	}

	private static void goTo(String var, String type) throws Exception {
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
				Thread.sleep(2);
				if(j<pToY) {
					j++;
				}
			}

		} else if(pFromX < pToX && pFromY > pToY) {
			for(i = (int) pFromX; i<pToX; i++) {
				initRobot().mouseMove(i, j);
				Thread.sleep(2);
				if(j>pToY) {
					j--;
				}
			}

		} else if(pFromX > pToX && pFromY < pToY) {
			for(i = (int) pFromX; i>pToX; i--) {
				initRobot().mouseMove(i, j);
				Thread.sleep(2);
				if(j<pToY) {
					j++;
				}
			}

		} else if(pFromX > pToX && pFromY > pToY) {
			for(i = (int) pFromX; i>pToX; i--) {
				initRobot().mouseMove(i, j);
				Thread.sleep(2);
				if(j>pToY) {
					j--;
				}
			}
		}
	}

	private static void click(String var, String type) throws Exception {
		if (type.equals("xpath")) {
			driver.findElement(By.xpath(var)).click();
		} else if (type.equals("id")) {
			driver.findElement(By.id(var)).click();
		} else if (type.equals("name")) {
			driver.findElement(By.name(var)).click();
		}	
		Thread.sleep(2000);
	}

	private void doubleclick(String var, String type) {
		Actions action = new Actions(driver);
		if (type.equals("xpath")) {
			action.moveToElement(driver.findElement(By.xpath(var)));
			action.doubleClick(driver.findElement(By.xpath(var))).build().perform();
		} else if (type.equals("id")) {
			action.moveToElement(driver.findElement(By.id(var)));
			action.doubleClick(driver.findElement(By.id(var))).build().perform();
		} else if (type.equals("name")) {
			action.moveToElement(driver.findElement(By.name(var)));
			action.doubleClick(driver.findElement(By.name(var))).build().perform();
		}
	}

	private void goToAndClick(String var, String type) throws Exception {
		goTo(var, type);
		click(var, type);
	}

	private void highlight(String var, String type) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		if (type.equals("xpath")) {
			js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", driver.findElement(By.xpath(var)));
		} else if (type.equals("id")) {
			js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", driver.findElement(By.id(var)));
		} else if (type.equals("name")) {
			js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", driver.findElement(By.name(var)));
		}
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
}
