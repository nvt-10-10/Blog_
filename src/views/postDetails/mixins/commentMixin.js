import { commentApi } from "@/api";
async function deleteComment(comments, id) {
  commentApi.deleteComment(id);
  updateComments(comments, id);
}
async function updateComments(comments, id) {
  const index = comments.value.findIndex((item) => item.comment.id === id);
  comments.value.splice(index, 1);
}
export { deleteComment, updateComments };
