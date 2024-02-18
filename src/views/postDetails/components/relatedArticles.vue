<template>
  <div class="related-articles">
    <div class="container">
      <h2 class="related-articles-heading">Bài viết khác từ {{ name }}</h2>
      <div class="post-related-list">
        <article class="post-related-item" v-for="item in posts" :key="item?.id">
          <div class="post-related-info">
            <h3 class="post-related-title">
              <router-link :to="{ path: '/post-details/' + item?.id }">
                {{ item?.title }}
              </router-link>
            </h3>
            <span class="post-related-user">{{ item?.user?.name }}</span>
            <div class="post-stats">
              <span class="post-stats-item">
                <img src="../../../assets/icons/view.svg" alt="" />
                {{ item?.count_view }}
              </span>
              <span class="post-stats-item">
                <img src="../../../assets/icons/comment.svg" alt="" />
                {{ item?.count_comment }}
              </span>
              <span class="post-stats-item">
                <img src="../../../assets/icons/like.svg" alt="" />
                {{ item?.count_like }}
              </span>
            </div>
          </div>
        </article>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    name: {
      type: String,
      default: "",
    },
    posts: {
      type: Array,
      default: null,
    },
  },
};
</script>
