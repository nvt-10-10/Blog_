<template>
  <div class="post">
    <div class="container">
      <div class="post-inner">
        <div class="post-left">
          <div class="post-content-top">
            <a href="#!">
              <div class="user-info">
                <img :src="author?.img" alt="" class="user-avt" />
                <span class="user-name">{{ author?.name }}</span>
              </div>
            </a>
            <div class="post-info">
              <span class="post-time">{{ post?.date }}</span>
              <div class="post-info-bottom">
                <div class="post-info-item">
                  <img src="../../../assets/icons/view.svg" alt="" />
                  <span>{{ post?.count_view }}</span>
                </div>
                <div class="post-info-item">
                  <img src="../../../assets/icons/comment.svg" alt="" />
                  <span>{{ post?.count_comment }} </span>
                </div>
                <div class="post-info-item">
                  <img v-if="is_like" src="../../../assets/icons/liked.svg" alt="" @click="updateLikePosts" />
                  <img v-else src="../../../assets/icons/like.svg" alt="" @click="updateLikePosts" />
                  <span>{{ post?.count_like }}</span>
                </div>
              </div>
            </div>
          </div>
          <div class="post-content">
            <h2 class="post-title">{{ post?.title }}</h2>
            <div class="post-main" v-html="post?.content"></div>
          </div>
        </div>
        <div class="post-right"></div>
      </div>
    </div>
  </div>
</template>
<script>
import { reactive, ref, watchEffect } from "vue";
export default {
  props: {
    post: {
      type: Object,
      default: null,
    },
    isLike: {
      type: Boolean,
      default: false,
    },
    updateLikePost: { type: Function, default: null },
  },
  setup(props) {
    const user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : null;
    const like_post_form = reactive({
      user_id: -1,
      post_id: -1,
      type: "post",
    });
    const author = ref(Object);
    const is_like = ref(false);
    watchEffect(() => {
      is_like.value = props.isLike;
      like_post_form.user_id = user?.id;
      like_post_form.post_id = props.post?.id;
      author.value = props.post?.user;
    });

    function updateLikePosts() {
      if (user) {
        props.updateLikePost();
      } else alert("Vui long dang nhap ");
    }
    return {
      like_post_form,
      is_like,
      author,
      updateLikePosts,
    };
  },
};
</script>
