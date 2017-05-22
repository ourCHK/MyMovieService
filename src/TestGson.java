import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TestGson {
	
	public static void main(String[] args) {
		String test = "May 21, 2017 8:02:08 PM";
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss",Locale.US);
//		Date date = Date.valueOf(test);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		try {
			System.out.println(format1.format(format.parse(test)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
