import com.soft.io.Resources;
import com.soft.pojo.User;
import com.soft.sqlSession.SqlSession;
import com.soft.sqlSession.SqlSessionFactory;
import com.soft.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class MybatisTest01 {

    @Test
    public void test01() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(3);
        user.setUsername("张三");
        //User o = sqlSession.selectOne("user.selectOne", user);
        List<Object> objects = sqlSession.selectList("user.selectList", null);
        for (Object object : objects) {
            System.out.println(object);
        }
    }
}
