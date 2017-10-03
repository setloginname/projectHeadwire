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
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

import javaAutomationFramework.CodeWords;

public class MainClass {

	static CodeWords codewords = new CodeWords();

	public static void main(String[] args) throws Exception {
		//Create a instance of GraphicsConfiguration to get the Graphics configuration
		//of the Screen. This is needed for ScreenRecorder class.
		GraphicsConfiguration gc = GraphicsEnvironment//
				.getLocalGraphicsEnvironment()//
				.getDefaultScreenDevice()//
				.getDefaultConfiguration();

		//Create a instance of ScreenRecorder with the required configurations
		ScreenRecorder screenRecorder = new ScreenRecorder(gc,
				new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
						CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
						DepthKey, (int)24, FrameRateKey, Rational.valueOf(15),
						QualityKey, 1.0f,
						KeyFrameIntervalKey, (int) (15 * 60)),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,"black",
						FrameRateKey, Rational.valueOf(30)),
				null);

		//start Screenrecorder
		screenRecorder.start();
		
		readFile();

//		highlight(driver, stopElement);
		screenRecorder.stop();

		codewords.quit();
	}
	
	public static void readFile () throws Exception {
		BufferedReader reader = null;
		
		try {
			String currentLine;
			reader = new BufferedReader(new FileReader("C://Users//Pascal//Desktop//textfile.txt"));
			while ((currentLine = reader.readLine()) != null) {
				String[] strArr = currentLine.split(";");

				String codeword= strArr[0];
				String var = strArr[1];
				String type = strArr[2];

				codewords.execute(codeword, var, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)reader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}	
	}
}
