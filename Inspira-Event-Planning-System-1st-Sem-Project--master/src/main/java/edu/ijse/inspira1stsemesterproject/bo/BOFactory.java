package edu.ijse.inspira1stsemesterproject.bo;

import edu.ijse.inspira1stsemesterproject.bo.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {

    }
    public static BOFactory getInstance() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOType {
        USER,CUSTOMER,ITEM,SUPPLIER,SERVICE,PAYMENT,EMPLOYEE,CREATE_BOOKING,COMPLETE_EVENT;
    }

    public SuperBO getBO(BOType type){
        switch (type){
            case USER:
                return new UserBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case SERVICE:
                return new ServiceBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case CREATE_BOOKING:
                return new CreateBookingBOImpl();
            case COMPLETE_EVENT:
                return new CompleteEventBOImpl();
            default:
                return null;
        }

    }

}
