<template>
  <div class="text-center">
    <v-dialog v-model="dialog" width="500">
      <template v-slot:activator="{ on }">
        <v-btn class="error" dark v-on="on">Delete User</v-btn>
      </template>
      <v-card>
        <v-card-title class="headline grey lighten-2" primary-title
          >Delete User</v-card-title
        >
        <v-form class="px-3">
          <v-text-field
            label="Email"
            v-model="email"
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

	  <v-btn text class="error mx-0 mt-3" @click="submit">Delete User</v-btn>
        </v-form>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: "",
      password: "",
      emailRules: [
        value => !!value || "Email is required",
        value => /.+@.+/.test(value) || "Email must be valid"
      ],
      minRules: [value => value.length >= 8 || "Min 8 characters"]
    };
  },
  methods:{
	  submit(){
		  console.log(this.email, this.password)
	  }
  }
};
</script>
