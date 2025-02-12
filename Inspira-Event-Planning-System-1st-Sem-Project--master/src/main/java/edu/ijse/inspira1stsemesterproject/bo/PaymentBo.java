package edu.ijse.inspira1stsemesterproject.bo;

import edu.ijse.inspira1stsemesterproject.dto.PaymentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBo extends SuperBO{
    String getNextPaymentId() throws SQLException, ClassNotFoundException ;
    ArrayList<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException ;
    boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException ;
    boolean updatePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException ;
    public boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException ;
}
