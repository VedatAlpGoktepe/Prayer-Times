import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;	

public class PrayerOnline {

	public static void main(String[] args) throws Exception
	{
		JFrame window = new JFrame("Prayer Times");
		window.setSize(300,300);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel1 = new JPanel();
		
		JLabel allTimes = new JLabel();
		allTimes.setSize(100,200);
		allTimes.setFont(new Font("Arial", Font.PLAIN, 30));
		
		panel1.add(allTimes);
		window.add(panel1);
		
		URL url = new URL("https://www.muslimpro.com/en/Prayer-times-Toronto-Canada-6167865?date=&convention=ISNA&asrjuristic=Hanafi");

		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;
		int counter = 0;

		while((line = br.readLine()) != null)
		{
			counter++;
			if(counter == 19)
			{
				String[] helperArray = line.split("<span class=\"jam-solat\">");
				String helperString = helperArray[1] + helperArray[2] + helperArray[3] + helperArray[4] + helperArray[5] + helperArray[6];
				helperArray = helperString.split("</span>");
				
				allTimes.setText("<html>Fajr: " + helperArray[0] +
						"<br/>Sunrise: " + helperArray[2] + "<br/>Dhuhr: " + helperArray[4] +
						"<br/>Asr: " + helperArray[6] + "<br/>Maghrib: " + helperArray[8] +
						"<br/>Isha'a: " + helperArray[10] + "<html/>");
			}
		}
	}
}
