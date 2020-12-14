package sanchana.bagShop.asset.employee.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sanchana.bagShop.asset.employee.entity.Employee;

import java.util.List;


@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
    Employee findFirstByOrderByIdDesc();

    Employee findByNic(String nic);

}
