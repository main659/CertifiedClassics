import Vue from "vue";
import Vuex from "vuex";
import router from "../router/index";
import axios from "axios";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    email: "",
    password: "",
    loggedIn: false,
    certifications: [],
    vouchers: []
  },
  mutations: {
    loggedInMutation(state, value) {
      state.loggedIn = value;
    },
    emailMutation(state, value) {
      state.email = value;
    },
    passwordMutation(state, value) {
      state.password = value;
    },
    vouchersMutation(state, value) {
      state.vouchers = value;
    },
    certificationsMutation(state, value) {
      state.certifications = value;
    }
  },
  getters: {
    loggedIn(state) {
      return state.loggedIn;
    },
    email(state) {
      return state.email;
    },
    password(state) {
      return state.password;
    },
    certifications(state) {
      return state.certifications;
    },
    vouchers(state) {
      return state.vouchers;
    }
  },
  actions: {
    loginToApp({ commit, rootState }) {
      console.log(rootState.email);
      commit("loggedInMutation", true);
      localStorage.setItem("token", "exampleToken");
      router.push("/");
    },
    async createCertificationRequest(
      { commit, dispatch, rootState },
      certificationRequest
    ) {
      const url = `http://localhost:8080/certifications/`;
      const headers = {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("token")
      };
      console.log("=> Creating of new certification started...");
      try {
        const { data } = await axios.post(url, certificationRequest, { headers });
        console.log("=> HTTP POST call was successful");
        console.log(data);
      } catch (err) {
        console.log("=> HTTP POST failed");
        console.log(err);
      }
    }
  },
  modules: {}
});
