import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrayerOnline {

	public static void main(String[] args) throws Exception
	{
		JFrame window = new JFrame("Prayer Times");
		window.setSize(300,325);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		JPanel panel = new JPanel();

		JLabel times = new JLabel();
		times.setText("hello");
		times.setFont(new Font("Arial", Font.PLAIN, 30));

		String[] calcTypes = {"ISNA", "Diyanet"};
		String[] places = {"Toronto", "Ottawa"};

		JComboBox calc = new JComboBox(calcTypes);
		calc.setPreferredSize(new Dimension(120, 40));
		calc.setFont(new Font("Arial", Font.PLAIN, 18));
		JComboBox location = new JComboBox(places);
		location.setPreferredSize(new Dimension(120, 40));
		location.setFont(new Font("Arial", Font.PLAIN, 18));

		panel.add(times);
		panel.add(calc);
		panel.add(location);
		window.add(panel);

		printLocationTimes(times, location, calc);

		location.addActionListener (new ActionListener ()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					printLocationTimes(times, location, calc);
				}
				catch(Exception b){System.out.println("Error");}
			}
		});
		
		calc.addActionListener (new ActionListener ()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					printLocationTimes(times, location, calc);
				}
				catch(Exception b){System.out.println("Error");}
			}
		});
	}

	public static void printLocationTimes(JLabel times, JComboBox location, JComboBox calc) throws Exception
	{
		URL url = null;
		URLConnection urlCon;
		InputStream is;
		BufferedReader br;

		String local = (String)location.getSelectedItem();
		String provider = (String)calc.getSelectedItem();
		System.out.println(local);
		System.out.println(provider);

		if(local.equals("Toronto"))
		{
			if(provider.equals("ISNA"))
			{
				url = new URL("https://www.muslimpro.com/en/find?coordinates=43.653226%2C-79.3831843&country_code=CA&country_name=Canada&city_name=Toronto&date=&convention=ISNA&asrjuristic=Hanafi");
			}
			else if(provider.equals("Diyanet"))
			{
				url = new URL("https://www.muslimpro.com/en/find?coordinates=43.653226%2C-79.3831843&country_code=CA&country_name=Canada&city_name=Toronto&date=&convention=Diyanet&asrjuristic=Hanafi");
			}
		}
		else if(local.equals("Ottawa"))
		{
			if(provider.equals("ISNA"))
			{
				url = new URL("https://www.muslimpro.com/en/find?coordinates=45.4215296%2C-75.69719309999999&country_code=CA&country_name=Canada&city_name=Ottawa&date=&convention=ISNA&asrjuristic=Hanafi&highlat=Angle");
			}
			else if(provider.equals("Diyanet"))
			{
				url = new URL("https://www.muslimpro.com/en/find?coordinates=45.4215296%2C-75.69719309999999&country_code=CA&country_name=Canada&city_name=Ottawa&date=&convention=Diyanet&asrjuristic=Hanafi&highlat=Angle");
			}
		}

		urlCon = url.openConnection();
		is = urlCon.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));

		String line = null;
		int counter = 0;

		
		
		while((line = br.readLine()) != null)
		{
			counter++;
			if(counter == 43)
			{
				String[] helperArray = line.split("<span class=\"jam-solat\">");
				for(int i = 0; i < 6; i++)
				{
					helperArray[i] = helperArray[i+1].substring(0,5);
				}

				times.setText("<html>Fajr-----------" + helperArray[0] +
						"<br/>Sunrise------" + helperArray[1] + "<br/>Dhuhr--------" + helperArray[2] +
						"<br/>Asr------------" + helperArray[3] + "<br/>Maghrib------" + helperArray[4] +
						"<br/>Isha'a---------" + helperArray[5] + "<html/>");
			}
		}
	}
}