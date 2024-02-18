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
    async createUser({ commit }, params) {
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
    async login({ commit }, params) {
      commit("setLoading", true);
      await axios
        .post("/api/users/login", params)
        .then((response) => {
          commit("setItems", response.data);
          commit("setLoading", false);
          commit("setError", response.data?.id == null ? response.data?.error : null);
        })
        .catch((error) => {
          commit("setItems", []);
          commit("setLoading", false);
          commit("setError", error.message);
        });
    },
  },
  getters: {
    getUser: (state) => state,
  },
};
