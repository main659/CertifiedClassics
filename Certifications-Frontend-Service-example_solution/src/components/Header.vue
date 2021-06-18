<template>
  <v-app-bar app color="black" dark>
    <router-link to="/" tag="span">
      <div class="d-flex align-center" style="color:white; cursor: pointer">
        <h2>Certifications@IBM</h2>
      </div>
    </router-link>

    <v-spacer></v-spacer>

    <router-link to="/profile" style="color:white; cursor: pointer" tag="span">
      <span>Profile</span>
      <v-icon>mdi-account</v-icon>
    </router-link>
    <v-spacer></v-spacer>
    <span style="color:white; cursor: pointer" @click="logout">
      <span>Logout</span>
      <v-icon>mdi-logout</v-icon>
    </span>
  </v-app-bar>
</template>

<script>
import Vue from "vue";

export default Vue.extend({
  name: "Header",
  methods: {
    logout() {
      this.$store.commit("loggedInMutation", false);
      this.$router.push("/login");
      location.reload();
    }
  }
});
</script>
