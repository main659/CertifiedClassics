<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-card class="elevation-12">
          <v-toolbar dark color="black">
            <v-toolbar-title>{{ loginFormTitle }}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <p>{{ formDescriptionText }}</p>
            <v-form ref="form" v-model="valid" lazy-validation>
              <v-text-field
                prepend-icon="mdi-account"
                v-model="email"
                :counter="20"
                required
                :rules="emailRules"
                name="login"
                label="Login"
                type="text"
              ></v-text-field>
              <v-text-field
                id="password"
                prepend-icon="mdi-lock"
                name="password"
                v-model="password"
                :rules="minRules"
                label="Password"
                type="password"
                required
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn :disabled="!valid" color="primary" @click="login"
              >Login</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import Vue from "vue";
import { mapActions } from "vuex";

export default Vue.extend({
  name: "Login",
  data() {
    return {
      valid: true,
      loginFormTitle: "Welcome to Certifications app",
      formDescriptionText: "Please Enter your credentials",
      emailRules: [
        v => !!v || "E-mail is required",
        v => /.+@.+/.test(v) || "E-mail must be valid"
      ],
      minRules: [v => v.length >= 8 || "Min 8 characters"]
    };
  },
  computed: {
    email: {
      get() {
        return this.$store.state.email;
      },
      set(value) {
        this.$store.commit("emailMutation", value);
      }
    },
    password: {
      get() {
        return this.$store.state.password;
      },
      set(value) {
        this.$store.commit("passwordMutation", value);
      }
    }
  },
  methods: {
    ...mapActions(["loginToApp"]),
    login() {
      if (this.$refs.form.validate()) {
        this.valid = true;
      } else {
        this.valid = false;
      }
      if (this.valid) {
        this.loginToApp();
      }
    }
  }
});
</script>

<style scoped></style>
