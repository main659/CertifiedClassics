<template>
  <v-app>
    <div v-if="loggedIn">
    <component :is="this.changeHeader.header"></component>
    </div>
    <v-main>
      <router-view />
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import Footer from "./components/Footer.vue";
import Header from "./components/Header.vue";
import HeaderAdmin from "./components/HeaderAdmin.vue";
import { mapGetters, mapMutations } from "vuex";
import store from "./store";

export default {
  components: {
    Footer,
    Header,
    HeaderAdmin
  },
  computed: {
    ...mapGetters(["loggedIn", "isAdmin"]),

    changeHeader() {
      return store.getters["isAdmin"]
        ? {
            header: HeaderAdmin
          }
        : {
            header: Header
          };
    }
  },
  created() {
    this.loggedInMutation(localStorage.getItem("loggedIn"));
    this.isAdminMutation(localStorage.getItem("isAdmin"));
    if (this.loggedIn == false) {
      this.$router.push("/login");
    }
    
  },
  methods: {
    ...mapMutations(["loggedInMutation","isAdminMutation"])
  }
};
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
