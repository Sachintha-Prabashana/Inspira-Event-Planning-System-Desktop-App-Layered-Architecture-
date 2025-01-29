package edu.ijse.inspira1stsemesterproject.dao.custom.impl;

import edu.ijse.inspira1stsemesterproject.dao.custom.PaymentDAO;
import edu.ijse.inspira1stsemesterproject.dto.PaymentDto;
import edu.ijse.inspira1stsemesterproject.entity.Payment;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select payment_id from payment order by payment_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("P%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "P001";
    }

    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from payment");

        ArrayList<Payment> paymentLists = new ArrayList<>();

        while (rst.next()) {
            Payment payment = new Payment(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getDouble(3),
                    rst.getString(4)

            );
            paymentLists.add(payment);
        }
        return paymentLists;

    }

    public boolean save(Payment entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into payment values(?,?,?,?)",
                entity.getPaymentId(),
                entity.getPaymentDate(),
                entity.getPaymentAmount(),
                entity.getBookingId()

        );
    }

    public boolean update(Payment entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update payment set  date = ?, amount = ?, booking_id = ? where payment_id = ?",
                entity.getPaymentDate(),
                entity.getPaymentAmount(),
                entity.getBookingId(),
                entity.getPaymentId()
        );
    }

    @Override
    public Payment findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean delete(String paymentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from payment where payment_id=?", paymentId);
    }
}
