import axios from 'axios'
const EMPLOYEE_API_BASE_URL = 'http://localhost:8080/api/employees'

class EmployeeService {
  
  // Get all employees
  getEmployees() {
    return axios.get(EMPLOYEE_API_BASE_URL + '/all')
  }

  // delete employee
  deleteEmployee(employeeId) {
    return axios.delete(EMPLOYEE_API_BASE_URL + '/' + employeeId)
  }

  // update employee
  updateEmployee(employeeId, firstName, lastName, email) {
    return axios.put(EMPLOYEE_API_BASE_URL + '/' + employeeId, {
      firstName: firstName,
      lastName: lastName,
      email: email
    })
  }

  // add new employee
  addNewEmployee(firstName, lastName, email) {
    return axios.post(EMPLOYEE_API_BASE_URL + '/new', {
      firstName: firstName,
      lastName: lastName,
      email: email
    })
  }
}
export default new EmployeeService()