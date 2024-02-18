<template>
  <form :class="{ 'not-is-comment': !is_comment, 'form-comment': is_comment }" @submit.prevent="createComment">
    <div class="form-comment-group">
      <img :src="user?.img ? user?.img : '../../../assets/imgs/avt-default.png'" :alt="user.name" class="avt-user" />
      <textarea name="" id="" cols="30" rows="10" v-model="comment_form.content" class="form-comment-textarea" placeholder="Viết bình luận" required></textarea>
    </div>
    <div class="form-comment-btn">
      <button class="form-comment-cancel" v-if="is_edit" @click.prevent="cancelEdit">Hủy</button>
      <button class="form-comment-submit">{{ title }}</button>
    </div>
  </form>
</template>
<script>
import { reactive, ref, watchEffect } from "vue";
import { commentApi } from "@/api";
export default {
  props: {
    comment_id: {
      type: Number,
      default: -1,
    },
    is_edit: {
      type: Boolean,
      default: false,
    },
    is_reply: {
      type: Boolean,
      default: false,
    },

    is_comment: {
      type: Boolean,
      default: false,
    },

    comment_content: {
      type: String,
      default: "",
    },

    post_id: {
      type: Number,
      default: -1,
    },
  },
  setup(props, { emit }) {
    const user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : null;
    const comment_id = ref(-1);
    const is_edit = ref(false);
    const is_reply = ref(false);
    const is_comment = ref(false);
    const post_id = ref(-1);
    const comment_content = ref("");
    const comment_form = reactive({
      user_id: -1,
      comment_id: -1,
      content: "",
      post_id: -1,
    });
    let title = "Bình luận";
    watchEffect(() => {
      comment_id.value = props.comment_id;
      is_comment.value = props.is_comment;
      is_edit.value = props.is_edit;
      is_reply.value = props.is_reply;
      post_id.value = props.post_id;
      comment_content.value = props.comment_content;
      comment_form.user_id = user?.id;
      comment_form.comment_id = comment_id.value;
      comment_form.content = "";
      comment_form.post_id = post_id.value;
      if (is_edit.value) {
        comment_form.content = comment_content.value;
        title = "Lưu";
      } else {
        comment_form.content = "";
      }
    });
    const createComment = async () => {
      try {
        let data;
        if (is_reply.value) {
          data = await commentApi.createCommentReply(comment_form);
        } else if (is_edit.value) {
          data = await commentApi.updateComment(comment_form);
        } else {
          data = await commentApi.createComment(comment_form);
        }
        if (data.error === null) {
          const comment = data.data;

          if (is_edit.value) emit("reload-comment-edit", comment_form.content);
          else emit("reload-comment", comment);
          comment_form.content = "";
        } else {
          alert(data.error);
        }
      } catch (error) {
        console.error("Error:", error);
      }
    };
    const cancelEdit = () => {
      emit("cancel-comment");
    };
    return {
      comment_form,
      title,
      user,
      createComment,
      cancelEdit,
    };
  },
};
</script>
<style scoped>
.not-is-comment {
  margin-top: 30px;
  border: none;
  padding: 0;
}
.form-comment-btn {
  display: flex;
  justify-content: right;
  align-items: center;
  margin-top: 30px;
}
.form-comment-submit {
  margin-top: 0;
  margin-left: 0;
}
.form-comment-cancel {
  padding: 8px 16px;
  background-color: transparent;
  border-radius: 3px;
  font-weight: 500;
  font-size: 1.6rem;
  color: rgb(27, 27, 27);
}
</style>
