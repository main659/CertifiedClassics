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

    isAdmin: false,
    notFound: false,

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
    isAdminMutation(state, value){
      state.isAdmin = value;
    },
    notFoundMutation(state,value){
      state.notFound = value;
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
    isAdmin(state){
      return state.isAdmin;
    },
    notFound(state){
      return state.notFound;
    }
  },
  actions: {
    loginToApp({ commit, rootState }) {
      console.log(rootState.email);
      // login via backend API:
      axios.get("http://localhost:8080/users/search/email",{
        params:{
          email: this.state.email
        }
      }).then((result) =>{
        console.log("Found loading page....");
        commit("loggedInMutation", true);
        localStorage.setItem("loggedIn", "true");
        router.push("/");

      }).catch(error => {
        commit("notFoundMutation", true);
      })
     
      
    },
    async createCertificationRequest(
      { commit, rootState },
      certificationRequest
    ) {
      const url = "http://localhost:8080/certifications/";
      const headers = {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("token"),
      };
      try {
        const { data } = await axios.post(url, certificationRequest, {
          headers,
        });
        console.log(data);
      } catch (err) {
        console.log(err);
      }
    },
  },
  modules: {},
});
