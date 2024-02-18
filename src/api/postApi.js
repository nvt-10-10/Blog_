import store from "../store";

async function fetchPostByID(params) {
  await store.dispatch("post/fetchPostByID", params);
  return await store.getters["post/getPosts"];
}

async function fetchPostByUser(post_id, user_id) {
  await store.dispatch("post/fetchPostByUser", {
    user_id: user_id,
    post_id: post_id,
  });
  return await store.getters["post/getPosts"];
}

async function createPost(params) {
  await store.dispatch("post/createPost", params);
  return await store.getters["post/getPosts"];
}

export { fetchPostByID, fetchPostByUser, createPost };
