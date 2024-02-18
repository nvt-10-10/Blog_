async function updateCount(type, action, post, length, count = 1) {
  if (type == "like") {
    if (action == "+") {
      post.value.count_like = post.value.count_like + 1;
      length.value = length.value + 1;
    } else {
      post.value.count_like = post.value.count_like - 1;
      length.value = length.value - 1;
    }
  } else {
    if (action === "+") {
      post.value && (post.value.count_comment = (post.value.count_comment || 0) + 1);
    } else {
      post.value && (post.value.count_comment = (post.value.count_comment || 0) - count);
    }
  }
}
export { updateCount };
