<template>
  <main class="main">
    <div class="main-left">
      <img src="../../assets/imgs/registerImg.png" alt="" class="main-img" />
    </div>
    <div class="main-right">
      <h1 class="register-heading">Tạo tài khoản</h1>
      <div class="sign-in-group">
        <button class="sign-in"><img src="../../assets/imgs/google_img.jpg" alt="google" class="sign-in-img" /> Đăng kí với Google</button>
        <button class="sign-in"><img src="../../assets/imgs/facebook.png" alt="google" class="sign-in-img" /> Đăng kí với Facebook</button>
      </div>
      <p class="or">-Hoặc-</p>
      <form @submit.prevent="submitForm" class="form">
        <input type="text" name="name" id="" v-model="formData.name" class="form-input" placeholder="Full name" />
        <input type="email" name="email" class="form-input" placeholder="Email" v-model="formData.email" />
        <input type="password" name="email" class="form-input form-input-password" placeholder="Password" v-model="formData.password" />

        <button type="submit" class="form-submit">Tạo tài khoản</button>
        <span>Bạn đã có tài khoản? <a href="login">Đăng nhập</a></span>
      </form>
    </div>
  </main>
</template>
<script>
import axios from "@/config/axios-config";
export default {
  data() {
    return {
      formData: {
        name: "",
        email: "",
        password: "",
        username: "",
        address: "",
        date_of_birth: "",
      },
    };
  },
  methods: {
    submitForm() {
      axios
        .post("/api/users/create", this.formData)
        .then(() => {
          this.$router.push("/login");
        })
        .catch((error) => {
          if (error.response) {
            alert("Email ton tai");
            console.error("Server responded with an error:", error.response.data);
            this.errorMessage = error.response.data;
          }
        });
    },
  },
};
</script>
<style lang="scss">
@import "../../assets/scss/layouts/register.scss";
</style>
