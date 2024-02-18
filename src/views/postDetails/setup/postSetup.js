import { likeMixin } from "../mixins";
import { postApi } from "@/api";
async function fetchPostData(post, post_id, user_id, isLike, like_post_form) {
  const result = await postApi.fetchPostByID(post_id);
  post.value = result.data.post;
  likeMixin.checkLike(result.data.users_like, user_id, isLike);
  like_post_form.type_id = post.value.id;
}
async function fetchUserPosts(posts, user_id, post_id) {
  const result = await postApi.fetchPostByUser(post_id, user_id);
  posts.value = result.data;
}
export { fetchPostData, fetchUserPosts };
