import { commentApi } from "@/api";
async function fetchCommentByPostID(comments, post_id, page, length) {
  const response = await commentApi.fetchCommentByPostID(post_id, page);
  comments.value = response.data.comments || [];
  length.value = response.data.length;
}
function reloadComment(newComment, show_replies, show_reply_form, is_edit, is_reply, comments) {
  show_replies.value = true;
  show_reply_form.value = false;
  is_edit.value = false;
  is_reply.value = false;
  comments.value.unshift(newComment);
}
function reloadCommentEdit(content, comment, show_reply_form, is_edit, is_reply) {
  comment.value.content = content;
  show_reply_form.value = false;
  is_edit.value = false;
  is_reply.value = false;
  comment.value.edited = true;
}
async function handleShowReplies(show_replies, comment, comments) {
  show_replies.value = !show_replies.value;
  let response;
  try {
    response = await commentApi.fetchCommentReply(comment.value?.id);
    comments.value = response.data;
    comment.value.count_comment = 0;
  } catch (error) {
    console.error("Error fetching data:", response.error);
    throw error;
  }
}

export { fetchCommentByPostID, reloadComment, reloadCommentEdit, handleShowReplies };
