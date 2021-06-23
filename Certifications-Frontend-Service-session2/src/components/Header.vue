<template>
  <v-app-bar app dark>
    <router-link to="/" tag="span" class="pointerClass">
      <h2>Certifications@IBM</h2>
    </router-link>
    <v-spacer />
    <router-link to="/vouchers" tag="span" class="pointerClass">
      <span>Vouchers</span>
      <v-icon>mdi-alpha-v-box</v-icon>
    </router-link>
    <router-link to="/profile" tag="span" class="pointerClass">
      <span>Profile</span>
      <v-icon>mdi-account</v-icon>
    </router-link>
    <span class="pointerClass" @click="logoutFromApp">
      <span>Logout</span>
      <v-icon>mdi-logout</v-icon>
    </span>
  </v-app-bar>
</template>

<script>
import Vue from "vue";
import { mapActions, mapGetters, mapMutations } from "vuex";

export default {
  name: "Header",
  data() {
    return {
      loggedIn: this.$store.getters.loggedIn
    }
  },
  computed: {
    ...mapGetters(["loggedIn"])
  },
  methods: {
    ...mapActions(["logoutFromApp"]),
    ...mapMutations(["loggedInMutation"])
  }
};
</script>

<style scoped>
.pointerClass {
  cursor: pointer;
}
</style>
