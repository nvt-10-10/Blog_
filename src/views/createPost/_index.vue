<template>
  <Header />
  <div class="container" style="margin-top: 30px">
    <form class="" @submit.prevent="submitForm">
      <div class="form-group">
        <span class="title">Tiêu đề: </span>
        <input type="text" name="" v-model="from.title" id="" placeholder="Tiêu đề" required class="form-input" />
      </div>
      <div class="form-group">
        <span class="title">Nội dung: </span>
        <ckeditor :editor="editor" v-model="from.content" :config="editorConfig" class="form-input"></ckeditor>
      </div>
      <div class="form-group"><button class="form-submit-header">Đăng tin</button></div>
    </form>
  </div>
  <router-view></router-view>
</template>
<script>
import Header from "@/components/_header.vue";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { reactive, watchEffect } from "vue";
import { useRouter } from "vue-router";
export default {
  components: {
    Header,
  },
  setup() {
    const router = useRouter();
    const from = reactive({
      title: "",
      content: "<p>Content of the editor.</p>",
      user_id: 1,
    });
    watchEffect(() => {
      const user = JSON.parse(sessionStorage.getItem("user"));
      from.user_id = user ? user.id : -1;
      if (user == null) {
        alert("Vui long dang nhap");
        router.push("/login");
      }
    });
    async function submitForm() {
      this.$store.dispatch("createPost", from);
      this.show();
    }
    return {
      editor: ClassicEditor,
      editorData: "<p>Content of the editor.</p>",
      editorConfig: {},
      submitForm,
      from,
    };
  },
};
</script>
<style scope>
@import "../../assets/scss/base/reset.scss";
@import "../../assets/scss/base/base.scss";
@import "../../assets/scss/layouts/createPost.scss";
</style>
