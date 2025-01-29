package edu.ijse.inspira1stsemesterproject.bo.impl;

import edu.ijse.inspira1stsemesterproject.bo.PaymentBo;
import edu.ijse.inspira1stsemesterproject.dao.custom.PaymentDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.PaymentDAOImpl;
import edu.ijse.inspira1stsemesterproject.dto.EmployeeDto;
import edu.ijse.inspira1stsemesterproject.dto.PaymentDto;
import edu.ijse.inspira1stsemesterproject.entity.Employee;
import edu.ijse.inspira1stsemesterproject.entity.Payment;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBo {

    PaymentDAO paymentDAO = new PaymentDAOImpl();
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNextId();
    }

    public ArrayList<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        ArrayList<Payment> paymentsList = paymentDAO.getAll();
        for (Payment payment : paymentsList) {
            paymentDtos.add(
                    new PaymentDto(
                            payment.getPaymentId(),
                            payment.getPaymentDate(),
                            payment.getPaymentAmount(),
                            payment.getBookingId()
                    ));



        }
        return paymentDtos;

    }

    public boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(
                new Payment(
                        paymentDto.getPaymentId(),
                        paymentDto.getPaymentDate(),
                        paymentDto.getPaymentAmount(),
                        paymentDto.getBookingId()
                ));
    }

    public boolean updatePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(
                new Payment(
                        paymentDto.getPaymentId(),
                        paymentDto.getPaymentDate(),
                        paymentDto.getPaymentAmount(),
                        paymentDto.getBookingId()
                ));
    }

    public boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(paymentId);
    }
}
