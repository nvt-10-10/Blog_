function checkLike(user_like_id, user_id, like) {
  if (user_id == null || user_like_id == null) like.value = false;
  else if (user_like_id == -1) like.value = false;
  else like.value = user_like_id.split(",").includes(user_id.toString());
}
export { checkLike };
