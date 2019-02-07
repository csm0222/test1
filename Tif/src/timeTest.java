import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class timeTest {
	public static void main(String[] args) {
		List l = new ArrayList();
		HashMap m = null;
		// 시분초 000000 초기화
		Calendar cal = Calendar.getInstance();
		cal.set(cal.HOUR_OF_DAY, 00);
		cal.set(cal.MINUTE, 00);
		cal.set(cal.SECOND, 00);
	
		// interval 분 만큼 더한다.
		int intervalMin = 10;
		for(int i = 0; i < 1439; i++) {
			m = new HashMap();
			System.out.print(String.format("%02d%02d%02d", cal.get(cal.HOUR_OF_DAY) , cal.get(cal.MINUTE), cal.get(cal.SECOND) ) );
			cal.add(cal.MINUTE, intervalMin);
			System.out.println(String.format("  %02d%02d%02d", cal.get(cal.HOUR_OF_DAY) , cal.get(cal.MINUTE), cal.get(cal.SECOND) ) );
		}

		System.out.print(String.format("%02d%02d%02d", cal.get(cal.HOUR_OF_DAY) , cal.get(cal.MINUTE), cal.get(cal.SECOND) ) );
		cal.add(cal.MINUTE, intervalMin);
		System.out.println("  240000");
		Integer in = 5;
		int i = 5;
	}
}
