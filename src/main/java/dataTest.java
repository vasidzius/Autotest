import sun.util.calendar.LocalGregorianCalendar;

import java.time.Month;
import java.time.MonthDay;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Superuser on 15.12.2015.
 */
public class dataTest {

    public static void main(String...args) {
        Calendar c = new GregorianCalendar();
        System.out.println(c.getTime().getMonth());
        System.out.println(c.getTime());

        Month month = Month.of(c.getTime().getMonth()+1);
        Locale loc = Locale.forLanguageTag("ru");
        System.out.println(month.getDisplayName(TextStyle.FULL_STANDALONE, loc)); // Вернёт Январь

        int day = MonthDay.now().getDayOfMonth();

        System.out.println((int)Math.random()*1);
    }
}
