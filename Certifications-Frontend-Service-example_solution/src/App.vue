<template>
  <v-app>
    <Header v-if="loggedIn" />
    <v-main>
      <transition name="fade" mode="out-in">
        <router-view />
      </transition>
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import Vue from "vue";
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
import { mapGetters } from "vuex";

export default Vue.extend({
  name: "App",
  components: {
    Footer,
    Header
  },
  data: () => ({
    //
  }),
  computed: {
    ...mapGetters(["loggedIn"])
  }
});
</script>

<style lang="scss">
fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
