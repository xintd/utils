
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author yml
 * @Description TestMain
 * @date 2018-05-15 19:49
 **/
public class TestDate {

    public static void main(String[] args) {
        System.out.println(String.format("%02d", 9));

        System.out.println("======================================");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2000);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.YEAR, 2001);
        System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.getTime());
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH));
        SimpleDateFormat dateFmt = new SimpleDateFormat("dd");
        System.out.println(dateFmt.format(calendar.getTime()));

        System.out.println("===============================");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy - MM - dd");
        Calendar a = Calendar.getInstance();
        a.set(2018, Calendar.JUNE, 1);
        // 中间有没有下面这句话，最后执行结果会不一样
        a.getActualMaximum(Calendar.DAY_OF_MONTH);
        a.set(Calendar.DAY_OF_WEEK, 2);
        System.out.println(sdf.format(a.getTime()));
    }
}

