import { likeApi } from "@/api";
import { updateCount } from "../mixins/updateCount";
async function updateLikePost(isLike, like_post_form, post, user_id, length) {
  let result;
  if (!isLike.value) {
    result = likeApi.createLike(like_post_form);
    result
      .then((data) => {
        like_post_form.like_id = data.data.id;
        updateCount("like", "+", post, length);
      })
      .catch((error) => {
        console.error(error);
      });
  } else {
    result = likeApi.deleteLike(post.value.id, "post", user_id);
    updateCount("like", "-", post, length);
  }
  if (result.error == null) {
    isLike.value = !isLike.value;
  } else alert(result.error);
}
async function updateLikeComment(like, comment, like_id, user, form_like) {
  let result;
  if (!like.value) {
    comment.value.count_like = comment.value.count_like + 1;
    result = await likeApi.createLike(form_like);
    if (result.error === null) like_id.value = result.data.id;
    else alert(result.error);
  } else {
    comment.value.count_like = comment.value.count_like - 1;
    result = await likeApi.deleteLike(comment.value.id, "comment", user.id);
    console.log(result);
  }
  if (result.error != null) alert(result.error);
  else like.value = !like.value;
}
export { updateLikePost, updateLikeComment };
