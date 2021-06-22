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
    vouchers: [],
    users: []
  },
  mutations: {
    emailMutation(state, value) {
      state.email = value;
    },
    passwordMutation(state, value) {
      state.password = value;
    },
    loggedInMutation(state, value) {
      console.log("loggedInMutation: " + value);
      if (value === null) {
        state.loggedIn = false;
      } else {
        state.loggedIn = value;
      }
    },
    certificationsMutation(state, value) {
      state.certifications = value;
    },
    vouchersMutation(state, value) {
      state.vouchers = value;
    },
    usersMutation(state, value) {
      state.users = value;
    }
  },
  getters: {
    email(state: any) {
      return state.email;
    },
    password(state: any) {
      return state.password;
    },
    loggedIn(state) {
      return state.loggedIn;
    },
    certifications(state) {
      return state.certifications;
    },
    vouchers(state) {
      return state.vouchers;
    },
    users(state) {
      return state.users;
    }
  },
  actions: {
    loginToApp({ commit, rootState }) {
      console.log(rootState.email);
      // TODO login via backend API
      commit("loggedInMutation", true);
      localStorage.setItem("loggedIn", "true");
      router.push("/");
    },
    async createVoucherRequest({ commit, rootState }, voucherRequest) {
      const url = "http://localhost:8080/vouchers/";
      const headers = {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("token")
      };
      try {
        const { data } = await axios.post(url, voucherRequest, {
          headers
        });
        console.log(data);
      } catch (err) {
        console.log(err);
      }
    },
    async createUpdateVoucherRequest(
      { commit, rootState },
      voucherRequest
    ) {
      const url = "http://localhost:8080/vouchers/";
      const headers = {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("token")
      };
      try {
        const { data } = await axios.put(url, voucherRequest, {
          headers
        });
        console.log(data);
      } catch (err) {
        console.log(err);
      }
    },
    async createCertificationRequest(
      { commit, rootState },
      certificationRequest
    ) {
      const url = "http://localhost:8080/certifications/";
      const headers = {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("token")
      };
      try {
        const { data } = await axios.post(url, certificationRequest, {
          headers
        });
        console.log(data);
      } catch (err) {
        console.log(err);
      }
    }
    
  },
  
  modules: {}
});
