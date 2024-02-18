import store from "../store";
async function createLike(params) {
  await store.dispatch("like/createLike", params);
  return await store.getters["like/getLikes"];
}
async function deleteLike(type_id, type, user_id) {
  await store.dispatch("like/deleteLike", {
    type_id: type_id,
    type: type,
    user_id: user_id,
  });
  return await store.getters["like/getLikes"];
}
export { createLike, deleteLike };
