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
    isManager: false,
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
      if (value === null) {
        state.isAdmin = false;
      } else {
        state.isAdmin = value;
      }
    },
    isManagerMutation(state,value){
      if(value === null){
        state.isManager = false;
      }else{
        state.isManager = value;
      }
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
    isManager(state){
      return state.isManager;
    },
    notFound(state){
      return state.notFound;
    }
  },
  actions: {
    loginToApp({ commit, rootState }) {
      // login via backend API:
      axios.get("http://localhost:8080/users/search/email",{
        params:{
          email: this.state.email
        }
      }).then((result) =>{
        commit("loggedInMutation", true);
        commit("isAdminMutation",result.data.admin);
        console.log(result.data.admin);
        commit("isManagerMutation",result.data.manager);
        console.log(result.data.manager);
        localStorage.setItem("loggedIn", "true");
        localStorage.setItem("isAdmin",result.data.admin);
        localStorage.setItem("isManager",result.data.manager);
        router.push("/");
        

      }).catch(error => {
        commit("notFoundMutation", true);
      })
     
      
    },
    deleteUser(state,userEmail){
      axios.delete("http://localhost:8080/deleteUserByEmail/", {
        params:{
          userEmail: userEmail,
          adminEmail: this.state.email
        }
      }).then((result)=>{
        console.log("DONE");
      })
    },
    deleteCertification(state, id){
      axios.delete("http://localhost:8080//certifications/{id}",{
        params:{
          id : id
        }
      }
      ).then((result) =>{
        console.log("DELETED");
      })
    },
    promoteUser(rootState,userEmail){
      axios.get("http://localhost:8080/promoteManager/",{
        params:{
          userEmail: userEmail
        }
        
      }).then((result)=>{
        console.log("PROMOTED");
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
    saveCertificate(commit,certJson){
      const res = axios.post("http://localhost:8080/certifications", certJson);
      
    }
  },
  modules: {},
});
