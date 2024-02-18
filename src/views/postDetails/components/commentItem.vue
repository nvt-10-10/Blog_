<template>
  <article class="comment-item">
    <div v-if="!is_edit">
      <commentInfo :user="comment?.user" :editedProp="comment?.edited" :time="formatted_date" />
      <div class="comment-item-content">{{ comment?.content }}</div>
      <div class="comment-item-bottom">
        <a href="" :class="{ liked: liked }" @click.prevent="updateLike">Thích</a>
        <a href="" @click.prevent="toggleReplyForm">Trả lời</a>
        <a href="" v-if="comment?.user?.id == user?.id && !is_reply" @click.prevent="editComment">Chỉnh sửa</a>
        <a href="#" v-if="comment?.user?.id == user?.id" @click.prevent="deleteComment(comment_id)">Xóa</a>
        <div class="like">
          <img src="../../../assets/icons/like.svg" alt="" />
          {{ comment?.count_like }}
        </div>
      </div>
    </div>
    <div v-if="show_reply_form">
      <formComment :comment_id="comment?.id" :comment_content="comment?.content" :is_edit="is_edit" :is_reply="is_reply" @reload-comment="reloadComment" @reload-comment-edit="reloadCommentEdit" @cancel-comment="cancel" />
    </div>
    <div v-if="(comment && comment?.count_comment > 0) || show_replies">
      <a href="" style="margin-top: 10px; display: block" @click.prevent="handleShowReplies(comment?.id)" v-show="comment?.count_comment > 0"> Xem thêm {{ comment?.count_comment }} phản hồi </a>
      <div v-if="show_replies">
        <CommentItem v-for="item in comments" :key="item.comment.id" :data="item" @deleteItem="updateCommentDelete" :updateCountComment="updateCountComment" />
      </div>
    </div>
  </article>
</template>
<script>
import formComment from "./formComment.vue";
import commentInfo from "./commentInfo.vue";
import { commentApi } from "@/api";
import { likeMixin } from "../mixins";
import { onMounted, onUnmounted, watchEffect, reactive, ref } from "vue";
import { compareTime } from "@/utils/timeUtils.js";
import { commentSetup } from "../setup";
import { likeSetup } from "@/views/postDetails/setup";
import { commentMixin } from "@/views/postDetails/mixins";
export default {
  components: {
    formComment,
    commentInfo,
  },
  props: {
    data: Object,
    updateCountComment: Function,
  },
  setup(props, context) {
    const user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : null;
    const { data } = reactive(props);
    const comment = ref(Object);
    const comments = ref([]);
    const formatted_date = ref("");
    const show_reply_form = ref(false);
    const show_replies = ref(false);
    const is_edit = ref(false);
    const is_reply = ref(false);
    const like_id = ref(-1);
    const liked = ref(false);
    const edit = ref(false);
    const form_like = reactive({
      user_id: -1,
      type_id: -1,
      type: "comment",
    });
    watchEffect(() => {
      comment.value = data.comment;
      likeMixin.checkLike(data.user_id_like, user?.id, liked);
      form_like.user_id = user ? user?.id : null;
      form_like.type_id = comment.value ? comment.value.id : null;
    });
    async function handleShowReplies() {
      commentSetup.handleShowReplies(show_replies, comment, comments);
    }
    function toggleReplyForm() {
      if (!is_edit.value) show_reply_form.value = !show_reply_form.value;
      is_reply.value = !is_reply.value;
    }
    function reloadComment(newComment) {
      commentSetup.reloadComment(newComment, show_replies, show_reply_form, is_edit, is_reply, comments);
      props.updateCountComment("comment", "+");
    }
    function reloadCommentEdit(content) {
      commentSetup.reloadCommentEdit(content, comment, show_reply_form, is_edit, is_reply);
    }
    function editComment() {
      is_edit.value = true;
      show_reply_form.value = true;
      edit.value = true;
    }
    function cancel() {
      is_edit.value = false;
      show_reply_form.value = false;
    }
    async function deleteComment() {
      commentApi.deleteComment(comment?.value.id);
      props.updateCountComment("comment", "-");
      context.emit("deleteItem", comment?.value.id);
    }
    async function updateLike() {
      likeSetup.updateLikeComment(liked, comment, like_id, user, form_like);
    }
    async function updateCommentDelete(id) {
      props.updateCountComment("comment", "-", 1 + comments.value ? comments.value.length : 0);
      await commentMixin.deleteComment(comments, id);
    }
    onMounted(() => {
      formatted_date.value = compareTime(comment?.value.date);
      const interval = setInterval(() => {
        formatted_date.value = compareTime(comment?.value.date);
      }, 60000);
      onUnmounted(() => {
        clearInterval(interval);
      });
    });
    return {
      comments,
      comment,
      user,
      formatted_date,
      show_reply_form,
      show_replies,
      is_edit,
      is_reply,
      like_id,
      form_like,
      liked,
      edit,
      handleShowReplies,
      toggleReplyForm,
      reloadComment,
      reloadCommentEdit,
      editComment,
      cancel,
      deleteComment,
      updateLike,
      updateCommentDelete,
    };
  },
};
</script>
<style scoped>
.like {
  display: flex;
  align-items: center;
  gap: 10px;
}
.liked {
  color: #0e7aff;
  font-weight: 500;
}
</style>
