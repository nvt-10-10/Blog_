import store from "@/store";

async function login(params) {
  await store.dispatch("user/login", params);
  return await store.getters["user/getUser"];
}

async function createUser(params) {
  await store.dispatch("user/createUser", params);
  return await store.getters["user/getUser"];
}

export { createUser, login };
