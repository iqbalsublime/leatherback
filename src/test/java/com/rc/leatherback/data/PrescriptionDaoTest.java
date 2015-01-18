package com.rc.leatherback.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rc.core.test.util.AssertUtil;
import com.rc.leatherback.model.Prescription;

public class PrescriptionDaoTest {
    private IDatabaseTester databaseTester;
    private Connection connection;
    private PrescriptionDao dao;

    @Before
    public void loadData() throws Exception {
        FlatXmlDataFileLoader flatXmlDataFileLoader = new FlatXmlDataFileLoader();
        IDataSet dataSet = flatXmlDataFileLoader.load("/META-INF/fixtures/prescription.xml");

        this.databaseTester =
                new JdbcDatabaseTester("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/leatherback_test", "root",
                        "rockey.chen");
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        Class.forName("com.mysql.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/leatherback_test", "root", "rockey.chen");
        this.dao = new PrescriptionDao();
    }

    @After
    public void shutdownDbTester() throws Exception {
        if (null != databaseTester) {
            databaseTester.onTearDown();
        }

        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testGetById() throws ClassNotFoundException, SQLException {
        Prescription prescription = dao.getById(connection, 1);
        Assert.assertNotNull(prescription);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from table_prescription;");
        while (resultSet.next()) {
            Assert.assertEquals(resultSet.getLong("id"), prescription.getId());
            Assert.assertEquals(resultSet.getString("lot_number"), prescription.getLotNumber());
            Assert.assertEquals(resultSet.getDate("date"), prescription.getDate());
            Assert.assertEquals(resultSet.getString("part_number"), prescription.getPartNumber());
            Assert.assertEquals(resultSet.getString("part_number_head"), prescription.getPartNumberHead());
            Assert.assertEquals(resultSet.getString("part_number_body"), prescription.getPartNumberBody());
            Assert.assertEquals(resultSet.getDouble("total_amount"), prescription.getTotalAmount(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("total_price"), prescription.getTotalPrice(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("average_cost"), prescription.getAverageCost(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("hand"), prescription.getHand(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("total_amount_after_handed"), prescription.getTotalAmountAfterHanded(),
                    0.0001);
            Assert.assertEquals(resultSet.getString("created_by"), prescription.getCreatedBy());
            AssertUtil.AssertEqualDate(resultSet.getDate("created_date"), prescription.getCreatedDate());
            Assert.assertEquals(resultSet.getString("modified_by"), prescription.getModifiedBy());
            AssertUtil.AssertEqualDate(resultSet.getDate("modified_date"), prescription.getModifiedDate());
        }

        resultSet.close();
        statement.close();
    }

    @Test
    public void testFindAll() throws ClassNotFoundException, SQLException {
        List<Prescription> prescriptions = dao.findAll(connection);
        Assert.assertNotNull(prescriptions);
        Assert.assertNotEquals(0, prescriptions.size());

        Prescription prescription = prescriptions.get(0);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from table_prescription limit 1;");
        while (resultSet.next()) {
            Assert.assertEquals(resultSet.getLong("id"), prescription.getId());
            Assert.assertEquals(resultSet.getString("lot_number"), prescription.getLotNumber());
            Assert.assertEquals(resultSet.getDate("date"), prescription.getDate());
            Assert.assertEquals(resultSet.getString("part_number"), prescription.getPartNumber());
            Assert.assertEquals(resultSet.getString("part_number_head"), prescription.getPartNumberHead());
            Assert.assertEquals(resultSet.getString("part_number_body"), prescription.getPartNumberBody());
            Assert.assertEquals(resultSet.getDouble("total_amount"), prescription.getTotalAmount(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("total_price"), prescription.getTotalPrice(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("average_cost"), prescription.getAverageCost(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("hand"), prescription.getHand(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("total_amount_after_handed"), prescription.getTotalAmountAfterHanded(),
                    0.0001);
            Assert.assertEquals(resultSet.getString("created_by"), prescription.getCreatedBy());
            AssertUtil.AssertEqualDate(resultSet.getDate("created_date"), prescription.getCreatedDate());
            Assert.assertEquals(resultSet.getString("modified_by"), prescription.getModifiedBy());
            AssertUtil.AssertEqualDate(resultSet.getDate("modified_date"), prescription.getModifiedDate());
        }

        resultSet.close();
        statement.close();
    }

    @Test
    public void testAdd() throws ClassNotFoundException, SQLException {
        Prescription prescription = new Prescription();
        prescription.setLotNumber("20100708001");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 1, 12, 12, 23, 33);
        prescription.setDate(calendar.getTime());
        prescription.setPartNumber("TS-abcde");
        prescription.setPartNumberHead("TS");
        prescription.setPartNumberBody("abcde");
        prescription.setTotalAmount(12.345);
        prescription.setTotalPrice(23.4562);
        prescription.setAverageCost(45.678);
        prescription.setHand(1.2345);
        prescription.setTotalAmountAfterHanded(987.6432);

        dao.add(connection, prescription);
        Assert.assertNotEquals(0, prescription.getId());

        PreparedStatement statement = connection.prepareStatement("select * from table_prescription where id = ?;");
        statement.setLong(1, prescription.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Assert.assertEquals(resultSet.getLong("id"), prescription.getId());
            Assert.assertEquals(resultSet.getString("lot_number"), prescription.getLotNumber());
            AssertUtil.AssertEqualDate(resultSet.getDate("date"), prescription.getDate());
            Assert.assertEquals(resultSet.getString("part_number"), prescription.getPartNumber());
            Assert.assertEquals(resultSet.getString("part_number_head"), prescription.getPartNumberHead());
            Assert.assertEquals(resultSet.getString("part_number_body"), prescription.getPartNumberBody());
            Assert.assertEquals(resultSet.getDouble("total_amount"), prescription.getTotalAmount(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("total_price"), prescription.getTotalPrice(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("average_cost"), prescription.getAverageCost(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("hand"), prescription.getHand(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("total_amount_after_handed"), prescription.getTotalAmountAfterHanded(),
                    0.0001);
            Assert.assertEquals(resultSet.getString("created_by"), prescription.getCreatedBy());
            Assert.assertEquals(resultSet.getDate("created_date"), prescription.getCreatedDate());
            Assert.assertEquals(resultSet.getString("modified_by"), prescription.getModifiedBy());
            Assert.assertEquals(resultSet.getDate("modified_date"), prescription.getModifiedDate());
        }

        resultSet.close();
        statement.close();
    }

    @Test
    public void testUpdate() throws ClassNotFoundException, SQLException {
        Prescription prescription = dao.getById(connection, 1);
        prescription.setLotNumber("20100708001");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 1, 12, 12, 23, 33);
        prescription.setDate(calendar.getTime());
        prescription.setPartNumber("TS-abcde");
        prescription.setPartNumberHead("TS");
        prescription.setPartNumberBody("abcde");
        prescription.setTotalAmount(12.345);
        prescription.setTotalPrice(23.4562);
        prescription.setAverageCost(45.678);
        prescription.setHand(1.2345);
        prescription.setTotalAmountAfterHanded(987.6432);
        prescription.setCreatedBy("test");
        prescription.setModifiedBy("test2");
        prescription.setCreatedDate(new Date());
        prescription.setModifiedDate(DateUtils.addDays(new Date(), 1));
        dao.update(connection, prescription);

        Assert.assertEquals(1, prescription.getId());

        PreparedStatement statement = connection.prepareStatement("select * from table_prescription where id = ?;");
        statement.setLong(1, prescription.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Assert.assertEquals(resultSet.getLong("id"), prescription.getId());
            Assert.assertEquals(resultSet.getString("lot_number"), prescription.getLotNumber());
            AssertUtil.AssertEqualDate(resultSet.getDate("date"), prescription.getDate());
            Assert.assertEquals(resultSet.getString("part_number"), prescription.getPartNumber());
            Assert.assertEquals(resultSet.getString("part_number_head"), prescription.getPartNumberHead());
            Assert.assertEquals(resultSet.getString("part_number_body"), prescription.getPartNumberBody());
            Assert.assertEquals(resultSet.getDouble("total_amount"), prescription.getTotalAmount(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("total_price"), prescription.getTotalPrice(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("average_cost"), prescription.getAverageCost(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("hand"), prescription.getHand(), 0.0001);
            Assert.assertEquals(resultSet.getDouble("total_amount_after_handed"), prescription.getTotalAmountAfterHanded(),
                    0.0001);
            Assert.assertNotEquals(resultSet.getString("created_by"), prescription.getCreatedBy());
            AssertUtil.AssertNotEqualDate(resultSet.getDate("created_date"), prescription.getCreatedDate());
            Assert.assertEquals(resultSet.getString("modified_by"), prescription.getModifiedBy());
            AssertUtil.AssertEqualDate(resultSet.getDate("modified_date"), prescription.getModifiedDate());
        }

        resultSet.close();
        statement.close();
    }

    @Test
    public void testDelete() throws ClassNotFoundException, SQLException {
        Prescription prescription = dao.getById(connection, 1);
        dao.remove(connection, prescription.getId());

        PreparedStatement statement = connection.prepareStatement("select * from table_prescription where id = ?;");
        statement.setLong(1, prescription.getId());
        ResultSet resultSet = statement.executeQuery();
        Assert.assertFalse(resultSet.next());

        resultSet.close();
        statement.close();
    }
}
