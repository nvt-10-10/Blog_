import store from "../store";
async function createComment(params) {
  await store.dispatch("comment/createComment", params);
  return await store.getters["comment/getComments"];
}
async function createCommentReply(params) {
  await store.dispatch("comment/createCommentReply", params);
  return await store.getters["comment/getComments"];
}
async function updateComment(params) {
  await store.dispatch("comment/updateComment", params);
  return await store.getters["comment/getComments"];
}
async function deleteComment(id) {
  await store.dispatch("comment/deleteCommentByID", id);
  return await store.getters["comment/getComments"];
}
async function fetchCommentReply(id) {
  await store.dispatch("comment/fetchCommentReplyByCommentID", {
    comment_id: id,
  });
  return await store.getters["comment/getComments"];
}
async function fetchCommentByPostID(post_id, page) {
  await store.dispatch("comment/fetchCommentByPostID", { post_id: post_id, page: page });
  return await store.getters["comment/getComments"];
}
export { createComment, createCommentReply, updateComment, deleteComment, fetchCommentReply, fetchCommentByPostID };
