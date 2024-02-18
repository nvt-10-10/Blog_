<template>
  <Header />
  <main>
    <div class="post">
      <div class="container">
        <div class="post-inner">
          <div class="post-left">
            <div class="post-list">
              <article class="post-item" v-for="(item, index) in posts" :key="index">
                <div class="post-item-inner">
                  <img :src="item?.user?.img" alt="" class="user-avt" />
                  <div class="post-item-info">
                    <span class="user-name">{{ item?.user?.name }}</span>
                    <span class="post-item-time">{{ item?.date }}</span>
                    <router-link :to="{ path: 'post-details/' + item?.id }">
                      <p class="post-item-title">{{ item?.title }}</p>
                    </router-link>
                    <div class="post-stats">
                      <span class="post-stats-item">
                        <img src="../../assets/icons/view.svg" alt="" />
                        {{ item.count_view }}
                      </span>
                      <span class="post-stats-item">
                        <img src="../../assets/icons/comment.svg" alt="" />
                        {{ item.count_comment }}
                      </span>
                      <span class="post-stats-item">
                        <img src="../../assets/icons/like.svg" alt="" />
                        {{ item.count_like }}
                      </span>
                    </div>
                  </div>
                </div>
              </article>
            </div>
          </div>
          <div class="post-right"></div>
        </div>
      </div>
    </div>
  </main>
  <footer style="margin-top: 80px"></footer>
  <router-view></router-view>
</template>
<script>
// Import Axios
import axios from "@/config/axios-config";
import Header from "@/components/_header.vue";
import { onBeforeMount, ref } from "vue";
export default {
  components: {
    Header,
  },
  setup() {
    onBeforeMount(() => {
      fetchDataByPost();
    });
    const page = ref(0);
    const posts = ref([]);
    function fetchDataByPost() {
      axios
        .get(`api/posts/page/${page.value}`)
        .then((response) => {
          const data = response.data;
          posts.value = data;
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    }
    return {
      page,
      posts,
      fetchDataByPost,
    };
  },
};
</script>
<style lang="scss">
@import "../../assets/scss/base/reset.scss";
@import "../../assets/scss/base/base.scss";
@import "../../assets/scss/layouts/postDetails.scss";
@import "../../assets/scss/layouts/listPost.scss";
</style>
