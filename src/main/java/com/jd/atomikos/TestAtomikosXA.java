package com.jd.atomikos;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import javax.transaction.UserTransaction;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by zhangyanlong on 2017/7/27.
 */
public class TestAtomikosXA {

    public static void main(String[] args) throws Exception{

        AtomikosDataSourceBean  atomikosXADataSourceBean = new AtomikosDataSourceBean();
        atomikosXADataSourceBean.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        Properties properties = atomikosXADataSourceBean.getXaProperties();
        properties.put("URL","jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=UTF-8");
        properties.put("user","root");
        properties.put("password","root");
        atomikosXADataSourceBean.setUniqueResourceName("dataSource1");

        AtomikosDataSourceBean  atomikosXADataSourceBean1 = new AtomikosDataSourceBean();
       atomikosXADataSourceBean1.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        Properties properties1 = atomikosXADataSourceBean1.getXaProperties();
        properties1.put("URL","jdbc:mysql://localhost:3306/teacher?useUnicode=true&characterEncoding=UTF-8");
        properties1.put("user","root");
        properties1.put("password","root");
        atomikosXADataSourceBean1.setUniqueResourceName("dataSource2");
        UserTransaction utx = new UserTransactionImp();
        utx.setTransactionTimeout(100000);
        utx.begin();
        Connection connection = atomikosXADataSourceBean.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into student (name) values ('zhangsan')");
        Connection connection1 = atomikosXADataSourceBean1.getConnection();
        Statement statement1 = connection1.createStatement();
        statement1.executeUpdate("insert into teacher (name) values ('lisi')");
//        utx.rollback();
        utx.commit();
        System.out.println("=============");
    }


}
