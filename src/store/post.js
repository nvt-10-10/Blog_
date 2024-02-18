import axios from "../config/axios-config";
export default {
  namespaced: true,
  state: {
    data: [],
    loading: false,
    error: null,
  },
  mutations: {
    setItems(state, data) {
      state.data = data;
    },
    setLoading(state, loading) {
      state.loading = loading;
    },
    setError(state, error) {
      state.error = error;
    },
  },
  actions: {
    async fetchPostByID({ commit }, id) {
      commit("setLoading", true);
      await axios
        .get(`api/posts/${id}`)
        .then((response) => {
          commit("setItems", response.data);
          commit("setLoading", false);
          commit("setError", null);
        })
        .catch((error) => {
          commit("setLoading", false);
          commit("setError", error.message || "Error fetching items");
        });
    },
    async fetchPostByUser({ commit }, { user_id, post_id }) {
      commit("setLoading", true);
      await axios
        .get(`api/posts/user/${user_id}/post/${post_id}`)
        .then((response) => {
          commit("setItems", response.data);
          commit("setLoading", false);
          commit("setError", null);
        })
        .catch((error) => {
          commit("setError", error.message || "Error fetching items");
          commit("setLoading", false);
        });
    },
    async createPost({ commit }, params) {
      commit("setLoading", true);
      axios
        .post(`/api/posts/create`, params)
        .then((response) => {
          commit("setItems", response.data);
          commit("setLoading", false);
          commit("setError", null);
        })
        .catch((error) => {
          commit("setItems", []);
          commit("setLoading", false);
          commit("setError", error.message || "Error fetching items");
        });
    },
  },
  getters: {
    getPosts: (state) => state,
  },
};
