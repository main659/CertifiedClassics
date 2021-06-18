<template>
  <v-app-bar app dark>
    <router-link to="/" tag="span" class="pointerClass">
      <h2>Certifications@IBM</h2>
    </router-link>
    <v-spacer />
    <router-link to="/profile" tag="span" class="pointerClass">
      <span>ADMIN PAGE</span>
      <v-icon>mdi-omega</v-icon>
    </router-link>
    <v-spacer />
    <span class="pointerClass" @click="logout">
      <span>Logout</span>
      <v-icon>mdi-logout</v-icon>
    </span>
  </v-app-bar>
</template>

<script>
export default {
  name: "HeaderAdmin",
  methods: {
    logout() {
      this.$store.commit("loggedInMutation", false);
      this.$router.push("/login");
      localStorage.removeItem("loggedIn");
      location.reload();
    },
  },
};
</script>

<style scoped>
.pointerClass {
  cursor: pointer;
}
</style>
