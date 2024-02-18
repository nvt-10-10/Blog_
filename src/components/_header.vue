<template>
  <header class="header">
    <div class="container">
      <div class="header-inner">
        <img src="https://viblo.asia/logo_full.svg" alt="" class="logo" />
        <nav class="navbar">
          <ul class="navbar-list">
            <li class="navbar-item">
              <router-link :to="{ path: '/post-list' }">
                <a href="" class="navbar-link">Bài viết</a>
              </router-link>
            </li>
            <li class="navbar-item"><a href="" class="navbar-link">Hỏi đáp </a></li>
            <li class="navbar-item"><a href="" class="navbar-link">Thảo luận</a></li>
          </ul>
        </nav>
        <div class="navbar-right">
          <form action="" class="form-header">
            <input type="text" class="form-header-input" />
            <button class="form-header-submit">
              <img src="../assets/icons/search.svg" alt="" />
            </button>
          </form>
          <button class="navbar-right-button">
            <img src="../assets/icons/info.svg" alt="" />
          </button>
          <button class="navbar-right-button">
            <img src="../assets/icons/bell.svg" alt="" />
          </button>
          <button class="navbar-right-button">
            <router-link :to="{ path: '/post' }">
              <img src="../assets/icons/edit.svg" alt="" />
            </router-link>
          </button>
          <button class="navbar-right-button">
            <img src="../assets/icons/toggle.svg" alt="" />
          </button>
          <button class="navbar-right-button dropdown" v-if="user != null">
            <img :src="user?.img ? user?.img : '../assets/imgs/avt-default.png'" :alt="user.name" class="avt" />
            <div class="dropdown-menu" v-if="user != null">
              <a class="dropdown-item" @click="logout">Đăng xuất</a>
            </div>
          </button>
          <div v-else style="display: flex; align-items: center; cursor: pointer" @click="login">Đăng nhập / đăng ký</div>
        </div>
      </div>
    </div>
  </header>
</template>
<script>
import VueCookies from "vue-cookies";
import { useRouter } from "vue-router";
import { ref } from "vue";
export default {
  setup() {
    const user = ref(Object);
    const router = useRouter();
    const storedUser = sessionStorage.getItem("user");
    if (storedUser) {
      user.value = JSON.parse(storedUser);
    } else user.value = null;
    function login() {
      router.push("/login");
      VueCookies.set("url", router.currentRoute.value.path, 60 * 15 * 100);
    }
    function logout() {
      sessionStorage.removeItem("user");
      user.value = null;
    }
    return {
      user,
      login,
      logout,
    };
  },
};
</script>
<style lang="scss" scoped>
@import "../assets/scss/components/header.scss";
</style>
