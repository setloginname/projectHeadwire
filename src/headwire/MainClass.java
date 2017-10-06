package headwire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainClass {
	
	static CodeWords codewords = new CodeWords();

	public static void main(String[] args) throws Exception {
		
		readFile();
	}
	
	public static void readFile () throws Exception {
		BufferedReader reader = null;
		
		try {
			String currentLine;
			reader = new BufferedReader(new FileReader("C://Users//Pascal//Desktop//textfile.txt"));
			while ((currentLine = reader.readLine()) != null) {
				String[] strArr = currentLine.split(";");

				String codeword, var, type;
				if (strArr.length == 3) {
					codeword= strArr[0];
					var = strArr[1];
					type = strArr[2];
				} else if (strArr.length == 2) {
					codeword= strArr[0];
					var = strArr[1];
					type = null;
				} else {
					codeword= strArr[0];
					var = null;
					type = null;
				}
				codewords.execute(codeword, var, type);
				Thread.sleep(500);
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
