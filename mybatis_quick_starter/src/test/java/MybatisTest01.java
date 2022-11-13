import com.soft.dao.UserDao;
import com.soft.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

public class MybatisTest01 {

    @Test
    public void test01() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = null;
        sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(1);
        user.setUsername("lucy");
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User byCondition = userDao.findByCondition(user);

        User byCondition2 = userDao.findByCondition(user);
        System.out.println(byCondition == byCondition2);
        sqlSession.close();
    }
}
