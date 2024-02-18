<template>
  <main class="main">
    <div class="main-left">
      <img src="../../assets/imgs/registerImg.png" alt="" class="main-img" />
    </div>
    <div class="main-right">
      <h1 class="register-heading">Đăng nhập</h1>
      <div class="sign-in-group">
        <button class="sign-in"><img src="../../assets/imgs/google_img.jpg" alt="google" class="sign-in-img" /> Đăng nhập với Google</button>
        <button class="sign-in"><img src="../../assets/imgs/facebook.png" alt="google" class="sign-in-img" /> Đăng nhập với Facebook</button>
      </div>
      <p class="or">- Hoặc -</p>
      <form @submit.prevent="submitForm" class="form">
        <input type="email" name="email" class="form-input" placeholder="Email" v-model="form_login.email" />
        <input type="password" name="email" class="form-input form-input-password" placeholder="Password" v-model="form_login.password" />
        <button type="submit" class="form-submit-login">Đăng nhập</button>
        <span>Bạn chưa có tài khoản? <a href="register">Đăng kí</a></span>
      </form>
    </div>
  </main>
</template>
<script>
import VueCookies from "vue-cookies";
import { reactive } from "vue";
import { useRouter } from "vue-router";
import { userApi } from "@/api";
export default {
  setup() {
    const router = useRouter();
    const form_login = reactive({
      email: "",
      password: "",
    });
    async function submitForm() {
      const result = await userApi.login(form_login);
      if (result.error === null) {
        sessionStorage.setItem("user", JSON.stringify(result.data));
        let url = VueCookies.get("url");
        if (url !== null) {
          VueCookies.remove("url");
        } else {
          url = "/post-list";
        }
        router.push(url);
      } else {
        alert(result.error);
      }
    }
    return {
      form_login,
      submitForm,
    };
  },
};
</script>
<style lang="scss">
@import "../../assets/scss/base/reset.scss";
@import "../../assets/scss/base/base.scss";
@import "../../assets/scss/layouts/register.scss";
</style>
