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
    async createLike({ commit }, params) {
      commit("setLoading", true);
      axios
        .post("/api/likes", params)
        .then((response) => {
          commit("setLoading", false);
          commit("setItems", response.data);
          commit("setError", null);
        })
        .catch((error) => {
          commit("setLoading", false);
          commit("setError", error);
        });
    },
    async deleteLike({ commit }, { type_id, type, user_id }) {
      commit("setLoading", true);
      axios
        .delete(`/api/likes/type_id/${type_id}/type/${type}/user_id/${user_id}`)
        .then(() => {
          commit("setLoading", false);
          commit("setItems", "thành công");
          commit("setError", null);
        })
        .catch((error) => {
          commit("setLoading", false);
          commit("setError", error);
        });
    },
    async addLikeComment({ commit }, params) {
      commit("setLoading", true);
      axios
        .post("/api/likes/addLikeComment", params)
        .then((response) => {
          commit("setLoading", false);
          commit("setItems", response.data);
          commit("setError", null);
        })
        .catch((error) => {
          commit("setLoading", false);
          commit("setError", error);
        });
    },
    async addLikePost({ commit }, params) {
      commit("setLoading", true);
      axios
        .post("/api/likes/addLikePost", params)
        .then((response) => {
          commit("setLoading", false);
          commit("setItems", response.data);
          commit("setError", null);
        })
        .catch((error) => {
          commit("setLoading", false);
          commit("setError", error);
        });
    },
    async deleteLikeComment({ commit }, { like_id, comment_id }) {
      commit("setLoading", true);
      axios
        .delete(`/api/likes/deleteLikeComment/${like_id}/comment_id/${comment_id}`)
        .then(() => {
          commit("setLoading", false);
          commit("setError", null);
        })
        .catch((error) => {
          commit("setLoading", false);
          commit("setError", error);
        });
    },
    async deleteLikePost({ commit }, { like_id, post_id }) {
      commit("setLoading", true);
      axios
        .delete(`/api/likes/deleteLikePost/${like_id}/comment_id/${post_id}`)
        .then(() => {
          commit("setLoading", false);
          commit("setError", null);
        })
        .catch((error) => {
          commit("setLoading", false);
          commit("setError", error);
        });
    },
  },
  getters: {
    getLikes: (state) => state,
  },
};
