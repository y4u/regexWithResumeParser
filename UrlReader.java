package reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlReader {

	public static void main(String[] args) {
		//url Accessing
		try {
			URL resume =new URL("https://drive.google.com/open?id=1WjIT4SGLGwa8Q-9cswfbQhg-_iB6Rnnw");
			URLConnection yc=resume.openConnection();
			BufferedReader br= new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			while((inputLine=br.readLine())!=null) {
				System.out.println(inputLine);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
