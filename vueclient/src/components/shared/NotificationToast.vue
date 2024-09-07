<template>
  <div class="toast-container position-fixed bottom-0 end-0 p-3">
    <div 
      id="liveToast" 
      class="toast" 
      role="alert" 
      aria-live="assertive" 
      aria-atomic="true" 
      :class="toastClass"
      ref="toastElement"
    >
      <div class="toast-header">
        <strong class="me-auto">{{ title }}</strong>
        <button 
          type="button" 
          class="btn-close" 
          @click="hideToast" 
          aria-label="Close"
        ></button>
      </div>
      <div class="toast-body">
        {{ message }}
      </div>
    </div>
  </div>
</template>

<script>
import { Toast } from 'bootstrap/dist/js/bootstrap.bundle.min.js';

export default {
  name: "NotificationToast",
  props: {
    message: {
      type: String,
      required: true
    },
    type: {
      type: String,
      default: "success"
    },
    title: {
      type: String,
      default: "Notification"
    }
  },
  computed: {
    toastClass() {
      return this.type === "success" ? "bg-success text-light" : "bg-danger text-light";
    }
  },
  mounted() {
    this.showToast();
  },
  methods: {
    showToast() {
      const toastInstance = new Toast(this.$refs.toastElement);
      toastInstance.show();
    },
    hideToast() {
      const toastInstance = new Toast(this.$refs.toastElement);
      toastInstance.hide();
    }
  }
};
</script>

<style scoped>
.toast-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
