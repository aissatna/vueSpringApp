<template>
  <div class="container">
    <div class="d-flex justify-content-between align-items-center my-5">
      <h1 class="text-center mb-0">Employees View</h1>
      <button type="button" class="btn btn-success" @click="showModal('newEmployeeModal')">
        New Employee
      </button>
    </div>

    <table class="table table-striped text-center">
      <thead>
        <tr>
          <th>ID</th>
          <th>FirstName</th>
          <th>LastName</th>
          <th>Email</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="employee in employees" v-bind:key="employee.id">
          <td>{{ employee.id }}</td>
          <td>{{ employee.firstName }}</td>
          <td>{{ employee.lastName }}</td>
          <td>{{ employee.email }}</td>
          <td>
            <div class="btn-group" role="group" aria-label="Basic mixed styles example">
              <button type="button" class="btn btn-primary" @click="showUpdateModal(employee)">
                <font-awesome-icon :icon="editIcon" />
              </button>
              <button type="button" class="btn btn-danger" @click="showDeleteModal(employee.id)">
                <font-awesome-icon :icon="trashIcon" />
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>

  <!-- Delete Confirmation Modal -->
  <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="deleteModalLabel">Confirm Deletion</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          Are you sure you want to delete this employee?
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            Cancel
          </button>
          <button type="button" class="btn btn-danger" @click="confirmDelete">
            Delete
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- update employee modal -->
  <div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="updateModalLabel">Update Employee</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">

          <form class="row g-3">
            <div class="col-md-6">
              <label for="inputFirstName" class="form-label">First name</label>
              <input type="text" class="form-control" id="inputFirstName" required v-model="updateFirstName">
            </div>
            <div class="col-md-6">
              <label for="inputLastName" class="form-label">Last name</label>
              <input type="text" class="form-control" id="inputLastName" required v-model="updateLastName">
            </div>
            <div class="col-12">
              <label for="inputEmail" class="form-label">Email</label>
              <input type="email" class="form-control" id="inputEmail" required v-model="updateEmail">
            </div>
          </form>

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            Cancel
          </button>
          <button type="button" class="btn btn-primary" @click="confirmUpdate">Update</button>
        </div>
      </div>
    </div>
  </div>

  <!-- New Employee Modal -->
  <div class="modal fade" id="newEmployeeModal" tabindex="-1" aria-labelledby="newEmployeeModalLabel"
    aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="newEmployeeModalLabel">Create New Employee</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form class="row g-3">
            <div class="col-md-6">
              <label for="newFirstName" class="form-label">First name</label>
              <input type="text" class="form-control" id="newFirstName" required v-model="newFirstName">
            </div>
            <div class="col-md-6">
              <label for="newLastName" class="form-label">Last name</label>
              <input type="text" class="form-control" id="newLastName" required v-model="newLastName">
            </div>
            <div class="col-12">
              <label for="newEmail" class="form-label">Email</label>
              <input type="email" class="form-control" id="newEmail" required v-model="newEmail">
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="button" class="btn btn-success" @click="addNewEmployee">Create</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Toast component for success message -->
  <NotificationToast v-if="showSuccessToast" :message="toastMessage" type="success" title="Success" />

  <!-- Toast component for error message -->
  <NotificationToast v-if="showErrorToast" :message="toastMessage" type="error" title="Error" />


</template>


<script>
import EmployeeService from "../services/EmployeeService";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faEdit, faTrash } from "@fortawesome/free-solid-svg-icons";
import { Modal } from "bootstrap/dist/js/bootstrap.bundle.min.js";
import NotificationToast from "./shared/NotificationToast.vue";

export default {
  name: "EmployeeList",
  components: {
    FontAwesomeIcon,
    NotificationToast,
  },
  data() {
    return {
      employees: [],  // List of all employees
      editIcon: faEdit,
      trashIcon: faTrash,
      selectedEmployeeForDelete: null,
      selectedEmployeeForUpdate: null,

      // Update Employee Modal variables
      updateFirstName: "",
      updateLastName: "",
      updateEmail: "",

      // New Employee Modal variables
      newFirstName: "",
      newLastName: "",
      newEmail: "",
      // Toast variables
      showSuccessToast: false,
      showErrorToast: false,
      toastMessage: ''


    };
  },
  methods: {
    // get all employees action handler
    getEmployees() {
      EmployeeService.getEmployees().then((response) => {
        this.employees = response.data;
      });
    },
    // delete employee action handler
    showDeleteModal(employeeId) {
      this.selectedEmployeeForDelete = employeeId;
      this.$nextTick(() => {
        this.showModal("deleteModal");
      });
    },

    confirmDelete() {
      if (this.selectedEmployeeForDelete) {
        EmployeeService.deleteEmployee(this.selectedEmployeeForDelete).then(() => {
          this.getEmployees();
          this.hideModal("deleteModal")
          this.toastMessage = "Employee deleted successfully";
          this.showSuccessToast = true;
          this.hideToastAfterDelay();
        })
          .catch(() => {
            this.toastMessage = "Error deleting employee";
            this.showErrorToast = true;
            this.hideToastAfterDelay();
          });
      }
    },
    // edit employee action handler
    showUpdateModal(employee) {
      this.selectedEmployeeForUpdate = employee.id;
      this.updateFirstName = employee.firstName;
      this.updateLastName = employee.lastName;
      this.updateEmail = employee.email;
      this.$nextTick(() => {
        this.showModal("updateModal");
      });
    },

    confirmUpdate() {
      if (this.selectedEmployeeForUpdate) {
        EmployeeService.updateEmployee(this.selectedEmployeeForUpdate,
          this.updateFirstName,
          this.updateLastName,
          this.updateEmail)
          .then(() => {
            this.getEmployees();
            this.hideModal("updateModal");
            this.toastMessage = "Employee updated successfully";
            this.showSuccessToast = true;
            this.hideToastAfterDelay();
          })
          .catch(() => {
            this.toastMessage = "Error updating employee";
            this.showErrorToast = true;
            this.hideToastAfterDelay();
          });
      }
    },
    // add new employee action handler
    addNewEmployee() {
      if (this.newFirstName && this.newLastName && this.newEmail) {
        EmployeeService.addNewEmployee(this.newFirstName, this.newLastName, this.newEmail)
          .then(() => {
            this.getEmployees();
            this.hideModal("newEmployeeModal");
            this.newFirstName = "";
            this.newLastName = "";
            this.newEmail = "";
            this.toastMessage = "New employee added successfully";
            this.showSuccessToast = true;
            this.hideToastAfterDelay();
          })
          .catch(() => {
            this.toastMessage = "Error adding new employee";
            this.showErrorToast = true;
            this.hideToastAfterDelay();
          });
      }
    },

    // Toast Helpers functions
    hideToastAfterDelay() {
      setTimeout(() => {
        this.showSuccessToast = false;
        this.showErrorToast = false;
      }, 6000); // Delay 6 seconds
    },

    // Modal Helpers functions
    showModal(modalId) {
      const modalElement = document.getElementById(modalId);
      const modal = new Modal(modalElement);
      modal.show();
    },

    hideModal(modalId) {
      const modalElement = document.getElementById(modalId);
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.hide();
    },
  },
  created() {
    this.getEmployees();
  },
};
</script>


<style></style>