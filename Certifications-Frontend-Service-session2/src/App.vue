<template>
  <v-app>
    <Header />
    <v-main>
      <router-view />
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import Footer from "./components/Footer.vue";
import Header from "./components/Header.vue";
import { mapGetters, mapMutations } from "vuex";

export default {
  components: {
    Footer,
    Header,
  },
  computed: {
   
  },
  created() {
    this.loggedInMutation(localStorage.getItem("loggedIn"));
    if (this.loggedIn == false) {
      this.$router.push("/login");
    }
  },
  methods: {
    ...mapMutations(["loggedInMutation"]),
  },
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
