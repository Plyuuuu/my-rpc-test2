package github.veikkoroc.service.dao;

import github.veikkoroc.entry.Order;
import github.veikkoroc.service.OrderService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/27 12:18
 */
@Repository(value = "orderServiceDao")
public class OrderServiceDao {

    private static SqlSessionFactory sqlSessionFactory;

    /**
     * 初始化sqlSessionFactory
     */
    static {

        try {
            InputStream resourceAsStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
            resourceAsStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从数据库内查询订单通过id
     * @param id
     * @return
     */
    public Order getOrderById(int id) {
        Order orderById = null;
        SqlSession sqlSession = null;
        try{
            sqlSession = sqlSessionFactory.openSession();
            OrderService mapper = sqlSession.getMapper(OrderService.class);
            orderById = mapper.getOrderById(id);
        }
        finally {
            if (sqlSession!=null){
                sqlSession.close();
            }
        }
        return orderById;
    }

}
