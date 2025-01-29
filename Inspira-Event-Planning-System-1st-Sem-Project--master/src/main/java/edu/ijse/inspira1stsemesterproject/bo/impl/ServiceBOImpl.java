package edu.ijse.inspira1stsemesterproject.bo.impl;

import edu.ijse.inspira1stsemesterproject.bo.ServiceBO;
import edu.ijse.inspira1stsemesterproject.dao.custom.ServiceDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.ServiceDAOImpl;
import edu.ijse.inspira1stsemesterproject.dto.EmployeeDto;
import edu.ijse.inspira1stsemesterproject.dto.ServiceDto;
import edu.ijse.inspira1stsemesterproject.entity.Employee;
import edu.ijse.inspira1stsemesterproject.entity.Service;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceBOImpl implements ServiceBO {

    ServiceDAO serviceDAO = new ServiceDAOImpl();
    public ArrayList<String> getAllServiceIds() throws SQLException, ClassNotFoundException {
       return serviceDAO.getAllIds();
    }

    public ServiceDto findById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        Service service = serviceDAO.findById(selectedServiceId);
        return new ServiceDto(
                service.getServiceId(),
                service.getPrice(),
                service.getServiceType()
        );
    }

    public String getNextServiceId() throws SQLException, ClassNotFoundException {
       return serviceDAO.getNextId();
    }

    public ArrayList<ServiceDto> getAllServices() throws SQLException, ClassNotFoundException {
        ArrayList<ServiceDto> serviceDtos = new ArrayList<>();
        ArrayList<Service> services = serviceDAO.getAll();
        for (Service service : services) {
            serviceDtos.add(
                    new ServiceDto(
                            service.getServiceId(),
                            service.getPrice(),
                            service.getServiceType()
                    ));



        }
        return serviceDtos;
    }

    public boolean saveService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException {
        return serviceDAO.save(
                new Service(
                        serviceDto.getServiceId(),
                        serviceDto.getPrice(),
                        serviceDto.getServiceType()
                ));
    }

    public boolean updateService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException {
        return serviceDAO.update(
                new Service(
                        serviceDto.getServiceId(),
                        serviceDto.getPrice(),
                        serviceDto.getServiceType()
                ));
    }

    public boolean deleteService(String serviceId) throws SQLException, ClassNotFoundException {
        return serviceDAO.delete(serviceId);
    }
}
