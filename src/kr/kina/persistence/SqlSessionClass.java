package kr.kina.persistence;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/** SqlSession객체생성 클래스
 *  Created by Yoon on 2016-11-07.
 */
public class SqlSessionClass {

    private String resource;

    public SqlSessionClass(String resource) throws IOException {
        this.resource = resource;
    }

    public SqlSession getSqlSessionFactory() {

        SqlSession session = null;

        try {
            Reader reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            session = factory.openSession(ExecutorType.BATCH);
            System.out.println("SqlSession SUCCESS !! ");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }


}
