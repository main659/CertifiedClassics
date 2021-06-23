import Vue from "vue";
import Vuex from "vuex";
import router from "../router/index";
import axios from "axios";
import qs from "qs";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    name: "",
    surname: "",
    email: "",
    password: "",
    authToken: "",
    loggedIn: false,
    certifications: [],
    vouchers: [],
    users: []
  },
  mutations: {
    nameMutation(state, value) {
      state.name = value;
    },
    surnameMutation(state, value) {
      state.surname = value;
    },
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
    name(state: any) {
      return state.name;
    },
    surname(state: any) {
      return state.surname;
    },
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
    async logoutFromApp({ commit, rootState }) {
      const config = {
        headers: {
          'Content-Type': 'application/json',
          "auth_token": localStorage.getItem("auth_token"),
        }
      };
      
      localStorage.removeItem("auth_token");
      localStorage.setItem("loggedIn", "false");
      commit("loggedInMutation", false);
      router.push('/login');

      const response = await axios.get('http://localhost:8080/logout', config);
      console.log(response)
  },
    async createUser({ commit, rootState }) {

      const body = JSON.stringify({
        name: rootState.name,
        surname: rootState.surname,
        email: rootState.email,
        password: rootState.password
      });

      const config = {
        headers: {
          'Content-Type': 'application/json',
          "auth_token": localStorage.getItem("auth_token"),
        }
      };

      console.log(body);
      
      const response = await axios.post('http://localhost:8080/add-user', body, config)
      .then(res => {
        if(res.status === 200){
          router.push('/login');
        }
      });
  },
    async loginToApp({ commit, rootState }) {

        const body = qs.stringify({
          username: rootState.email,
          password: rootState.password
        });
  
        const config = {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'access-control-expose-headers': 'Set-Cookie, auth_token',
            'access-control-allow-headers': 'Content-Type, Custom-Header',
             withCredentials: true
          }
        };
        
        const response = await axios.post('http://localhost:8080/login', body, config)
        .then(res => {
          if(res.status === 200){
            localStorage.setItem("auth_token", res.headers['auth_token']);
            localStorage.setItem("loggedIn", "true");
            router.push('/');
          }
        });
    },
    async createVoucherRequest({ commit, rootState }, voucherRequest) {
      const url = "http://localhost:8080/vouchers/";
      const headers = {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("token"),
        "auth_token": localStorage.getItem("auth_token"),
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
      const url = "http://localhost:8080/vouchers/update/";
      const headers = {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("token"),
        "auth_token": localStorage.getItem("auth_token"),
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
    async createCertificationRequest(
      { commit, rootState },
      certificationRequest
    ) {
      const url = "http://localhost:8080/certifications/";

      const config = {
        headers: {
          "Content-Type": "application/json",
          "auth_token": localStorage.getItem("auth_token"),
        }
      };

      try {
        const { data } = await axios.post(url, certificationRequest, config);
        console.log(data);
      } catch (err) {
        console.log(err);
      }
    }
  },
  
  modules: {}
});
