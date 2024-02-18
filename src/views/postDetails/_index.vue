<template>
  <Header />
  <main>
    <postContent :isLike="isLike" :post="post" :updateLikePost="updateLikePost" />
    <relatedArticles :name="post?.name" :posts="posts" />
    <comment :data="comments" :post_id="post?.id" :fetchCommentByPostID="fetchCommentByPostID" :updateCountComment="updateCountComment" />
  </main>
  <footer style="margin-top: 80px"></footer>
  <router-view></router-view>
</template>
<script>
import Header from "@/components/_header.vue";
import postContent from "./components/postContent.vue";
import relatedArticles from "./components/relatedArticles.vue";
import comment from "./components/_comment.vue";
import { reactive, ref, watch } from "vue";
import { useRoute } from "vue-router";
import { commentSetup, postSetup, likeSetup } from "./setup";
import { updateCount } from "./mixins/updateCount";
export default {
  components: {
    Header,
    postContent,
    relatedArticles,
    comment,
  },
  setup() {
    const route = useRoute();
    const user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : null;
    const post = ref(Object);
    const posts = ref([]);
    const comments = ref([]);
    const page_comment = ref(0);
    const length = ref(0);
    const isLike = ref(false);
    const edit = ref(false);
    const comment_form = reactive({
      content: "",
      user_id: user?.id,
      post_id: route.params.id,
    });
    const like_post_form = reactive({
      user_id: user?.id,
      type_id: post.value?.id,
      type: "post",
    });
    async function updateCountComment(type, action, count = 1) {
      updateCount(type, action, post, length, count);
    }
    async function updateLikePost() {
      await likeSetup.updateLikePost(isLike, like_post_form, post, user?.id, length);
    }
    async function fetchPostByUser() {
      await postSetup.fetchUserPosts(posts, post?.value?.user?.id, post?.value?.id);
    }
    async function fetchPostData() {
      const post_id = route.params.id;
      await postSetup.fetchPostData(post, post_id, user?.id, isLike, like_post_form);
    }
    async function fetchCommentByPostID() {
      await commentSetup.fetchCommentByPostID(comments, post.value?.id, page_comment.value, length);
    }
    watch(
      () => route.params.id,
      async (newId, oldId) => {
        comment_form.post_id = newId;
        if (newId !== oldId) {
          await fetchPostData();
          await Promise.all([fetchCommentByPostID(), fetchPostByUser()]);
        }
      },
      { immediate: true }
    );
    return {
      post,
      posts,
      comments,
      page_comment,
      length,
      isLike,
      edit,
      comment_form,
      like_post_form,
      fetchPostByUser,
      fetchPostData,
      fetchCommentByPostID,
      updateLikePost,
      updateCountComment,
    };
  },
};
</script>
<style lang="scss">
@import "../../assets/scss/base/reset.scss";
@import "../../assets/scss/base/base.scss";
@import "../../assets/scss/layouts/postDetails.scss";
</style>
