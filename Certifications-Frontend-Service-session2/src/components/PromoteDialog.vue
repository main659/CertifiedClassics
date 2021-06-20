<template>
  <div class="text-center">
    <v-dialog width="500">
      <template v-slot:activator="{ on }">
        <v-btn color="primary"
  elevation="2"
  large dark v-on="on"
  class="ma-2"
  style="width:200px;">
  <span class="text-truncate" style="width:1500px;">Promote User</span></v-btn>
      </template>
      <v-card>
        <v-card-title class="headline grey lighten-2" primary-title
          >Promote User</v-card-title
        >
        <v-form class="px-3">
          <v-text-field
            label="Email"
            v-model="userEmail"
            prepend-icon="mdi-account"
            :rules="emailRules"
          ></v-text-field>

          <v-text-field
            label="Admin Password"
            v-model="password"
            prepend-icon="mdi-account"
            :rules="minRules"
            type="password"
          ></v-text-field>

          <v-btn text class="primary mx-0 mt-3" @click="promoteUser(userEmail)"
            >Promote User</v-btn
          >
        </v-form>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
export default {
  data() {
    return {
      userEmail: "",
      password: "",
      emailRules: [
        value => !!value || "Email is required",
        value => /.+@.+/.test(value) || "Email must be valid"
      ],
      minRules: [value => value.length >= 8 || "Min 8 characters"]
    };
  },
  methods: {
    ...mapActions(["promoteUser"])
  }
};
</script>
