<template>
  <div class="comment">
    <div class="container">
      <div v-if="user !== null && user?.id !== -1">
        <div class="comment-title">Bình luận</div>
        <formComment :is_comment="true" :post_id="post_id" @reload-comment="reloadComment" />
      </div>
      <div v-else>
        <a @click.prevent="login" style="cursor: pointer">Vui lòng đăng nhập để bình luận</a>
      </div>
      <div class="comment-list">
        <commentItem v-for="item in comments" :key="item.comment.id" :data="item" @deleteItem="updateCommentDelete" :updateCountComment="updateCountComment" />
      </div>
    </div>
  </div>
</template>
<script>
import { ref } from "vue";
import formComment from "./formComment.vue";
import commentItem from "./commentItem.vue";
import { watchEffect } from "vue";
import { useRouter } from "vue-router";
import VueCookies from "vue-cookies";
import { commentMixin } from "../mixins";
export default {
  components: {
    formComment,
    commentItem,
  },
  props: {
    data: {
      type: Array,
      default: null,
    },
    post_id: {
      type: Number,
      default: -1,
    },
    fetchCommentByPostID: Function,
    updateCountComment: Function,
  },
  setup(props) {
    const route = useRouter();
    const user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : null;
    const comments = ref([]);
    watchEffect(() => {
      comments.value = props.data;
    });
    function reloadComment(data) {
      props.updateCountComment("comment", "+");
      if (comments?.value.length > 0) {
        comments.value.unshift(data);
      } else {
        props.fetchCommentByPostID();
      }
    }
    async function updateCommentDelete(id) {
      props.updateCountComment("comment", "-");
      commentMixin.updateComments(comments, id);
    }
    function login() {
      route.push("/login");
      VueCookies.set("url", route.currentRoute.value.path, 60 * 15 * 100);
    }
    return {
      comments,
      reloadComment,
      user,
      login,
      updateCommentDelete,
    };
  },
};
</script>
