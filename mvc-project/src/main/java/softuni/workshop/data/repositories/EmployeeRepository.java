package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entities.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long> {

    Employee findByFirstNameAndLastName(String firstName, String lastName);

    List<Employee> findByAgeGreaterThan(int limitingAge);
}
