<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex md8 sm8 xs12>
        <v-card class="elevation-12">
          <v-toolbar dark color="black">
            <v-toolbar-title>Crate User Account</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form ref="form" v-model="validForm">
              <v-text-field
                prepend-icon=""
                label="Name"
                type="text"
                :rules="nameRules"
                v-model="name"
              >
              </v-text-field>
              <v-text-field
                prepend-icon=""
                label="Surname"
                type="text"
                :rules="surnameRules"
                v-model="surname"
              >
              </v-text-field>
              <v-text-field
                prepend-icon=""
                label="Email Address"
                type="text"
                :rules="emailRules"
                v-model="email"
              ></v-text-field>
              <v-text-field
                prepend-icon=""
                label="Password"
                type="text"
                :rules="passwordRules"
                v-model="password"
              ></v-text-field>
            </v-form>
          </v-card-text>
        </v-card>
        <v-card-actions>
          <v-btn :disabled="!validForm" color="primary" @click="createUser">
            Create User Account
          </v-btn>
        </v-card-actions>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "Registration",
  data() {
    return {
      validForm: true,
      nameRules: [value => !!value || "Name is required"],
      surnameRules: [value => !!value || "Surname is required"],
      emailRules: [value => !!value || "Email is required"],
      passwordRules: [value => !!value || "Password is required"]
    };
  },
  computed: {
    name: {
      get() {
        return this.$store.getters.name;
      },
      set(value) {
        this.$store.commit("nameMutation", value);
      }
    },
    surname: {
      get() {
        return this.$store.getters.surname;
      },
      set(value) {
        this.$store.commit("surnameMutation", value);
      }
    },
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
    ...mapActions(["createUser"])
  }
};
</script>
