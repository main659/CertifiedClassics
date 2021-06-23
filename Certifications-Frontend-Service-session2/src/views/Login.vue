<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex md4 sm8 xs12>
        <v-card class="elevation-12">
          <v-toolbar dark color="black">
            <v-toolbar-title>Welcome to Certifications app</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <p>Please Enter your credentials</p>
            <v-form ref="form" v-model="validForm">
              <v-text-field
                prepend-icon="mdi-account"
                label="Login"
                type="text"
                :counter="20"
                :rules="emailRules"
                v-model="email"
              >
              </v-text-field>
              <v-text-field
                prepend-icon="mdi-lock"
                label="Password"
                type="password"
                :rules="minRules"
                v-model="password"
              >
              </v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn
              left
              :disabled="!validForm"
              color="primary"
              @click="loginToApp"
              >Login</v-btn
            >
            <v-spacer></v-spacer>
            <v-btn right color="green" to="/registration">Sign Up</v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "Login",
  data() {
    return {
      validForm: true,
      emailRules: [
        value => !!value || "Email is required",
        value => /.+@.+/.test(value) || "Email must be valid"
      ],
      minRules: [value => value.length >= 8 || "Min 8 characters"]
    };
  },
  computed: {
    email: {
      get() {
        return this.$store.getters.email;
      },
      set(value) {
        this.$store.commit("emailMutation", value);
      }
    },
    password: {
      get() {
        return this.$store.getters.password;
      },
      set(value) {
        this.$store.commit("passwordMutation", value);
      }
    }
  },
  methods: {
    ...mapActions(["loginToApp"])
  }
};
</script>
