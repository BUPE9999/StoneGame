import java.util.List;

import static org.junit.Assert.*;

import com.game.stone.model.Record;
import com.game.stone.util.DbUtil;
import org.junit.Test;
import org.junit.BeforeClass;


public class DbTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        DbUtil.initData();
    }

    @Test
    public void testTop() {
        List<Record> list = DbUtil.getTopRanking(5);
        System.out.printf("list.size="+list.size());
        assertEquals(list.size()<=5,true);
    }

}
