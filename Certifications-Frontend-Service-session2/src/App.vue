<template>
  <v-app>
    <div v-if="loggedIn">
      <keep-alive>
        <component v-bind:is="this.changeHeader.header"></component>
      </keep-alive>
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
import HeaderManager from "./components/HeaderManager.vue";
import { mapGetters, mapMutations } from "vuex";
import store from "./store";

export default {
  components: {
    Footer,
    Header,
    HeaderAdmin,
    HeaderManager
  },
  computed: {
    ...mapGetters(["loggedIn", "isAdmin", "isManager"]),

    changeHeader() {
      if (store.getters["isAdmin"]) {
        return { header: HeaderAdmin };
      } else {
        if (store.getters["isManager"]) {
          return { header: HeaderManager };
        } else {
          return { header: Header };
        }
      }
    }
  },
  created() {
    this.loggedInMutation(localStorage.getItem("loggedIn"));
    this.isAdminMutation(localStorage.getItem("isAdmin"));
    this.isManagerMutation(localStorage.getItem("isManager"));
    if (this.loggedIn == false) {
      this.$router.push("/login");
    }
  },
  methods: {
    ...mapMutations([
      "loggedInMutation",
      "isAdminMutation",
      "isManagerMutation"
    ])
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
