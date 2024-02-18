<template>
  <div class="comment-item-info">
    <figure>
      <img :src="user?.img ? user?.img : '../../../assets/imgs/avt-default.png'" class="comment-user-avt" :alt="user?.name" />
    </figure>
    <div class="comment-info">
      <a href="">
        <h3 class="comment-user-name">{{ user?.name }}</h3>
      </a>
      <span class="comment-time">{{ time }}</span>
    </div>
    <div class="edited" v-if="edited">Đã chỉnh sửa</div>
  </div>
</template>
<script>
import { ref, watchEffect } from "vue";
export default {
  props: {
    user: {
      type: Object,
      default: null,
    },
    editedProp: {
      type: Boolean,
      default: false,
    },
    time: {
      type: String,
      default: "Loi ",
    },
  },
  setup(props) {
    const edited = ref(false);
    watchEffect(() => {
      edited.value = props.editedProp;
    });
    return {
      edited,
    };
  },
};
</script>
<style>
.edited {
  margin-left: auto;
}
</style>
