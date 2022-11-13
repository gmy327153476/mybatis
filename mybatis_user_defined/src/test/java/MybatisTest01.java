import com.soft.dao.IUserDao;
import com.soft.io.Resources;
import com.soft.pojo.User;
import com.soft.sqlSession.SqlSession;
import com.soft.sqlSession.SqlSessionFactory;
import com.soft.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

public class MybatisTest01 {

    @Test
    public void test01() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(1);
        user.setUsername("lucy");
        //User o = sqlSession.selectOne("user.selectOne", user);
        //List<Object> objects = sqlSession.selectList("user.selectList", null);
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        /*List<User> objects = userDao.findAll();
        for (Object object : objects) {
            System.out.println(object);
        }*/

        User byCondition = userDao.findByCondition(user);
        System.out.println(byCondition);

    }
}
