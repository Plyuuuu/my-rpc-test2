package github.veikkoroc.mybatis;

import github.veikkoroc.entry.Order;
import github.veikkoroc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/27 12:37
 */
@Slf4j
public class OrderServiceTest {
    /**
     * 初始化SqlSession工厂
     */
    private static SqlSessionFactory sqlSessionFactory;

    /**
     * @BeforeClass 要与 static 一起使用，就是在@Test之前执行
     */
    @BeforeClass
    public static void init(){
        try {
            InputStream resourceAsStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
            resourceAsStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询Order通过id
     */
    @Test
    public void test01(){
          log.info("SqlSessionFactory:[{}]",sqlSessionFactory);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrderService mapper = sqlSession.getMapper(OrderService.class);

        Order orderById = mapper.getOrderById(1);

        sqlSession.close();

        System.out.println(orderById);

    }
}
