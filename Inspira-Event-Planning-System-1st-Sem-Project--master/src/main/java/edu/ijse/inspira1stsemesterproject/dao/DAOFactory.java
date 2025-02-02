package edu.ijse.inspira1stsemesterproject.dao;

import edu.ijse.inspira1stsemesterproject.dao.custom.UserDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }
    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOType {
        USER,SUPPLIER,SERVICE,PAYMENT,ITEM,EVENT_SUPPLIER,EVENT,EMPLOYEE,CUSTOMER,BOOKING_SERVICE,BOOKING;
    }

    public  SuperDAO getDao(DAOType type){
        switch (type){
            case USER:
                return new UserDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case SERVICE:
                return new ServiceDAOImpl();
            case PAYMENT:
                 return new PaymentDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case EVENT_SUPPLIER:
                return new EventSupplierDAOImpl();
            case EVENT:
                 return new EventDAOImpl();
            case EMPLOYEE:
                 return new EmployeeDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case BOOKING_SERVICE:
                return new BookingServiceDAOImpl();
            case BOOKING:
                  return new BookingDAOImpl();
            default:
                return null;
        }

    }

}
