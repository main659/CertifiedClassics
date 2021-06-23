<template>
  <div class="text-center">
    <v-dialog width="500">
      <template v-slot:activator="{ on }">
        <v-btn
          color="primary"
          elevation="2"
          large
          dark
          v-on="on"
          class="ma-2"
          style="width:200px;"
        >
          <span class="text-truncate" style="width:1500px;"
            >Add Certificate</span
          ></v-btn
        >
      </template>
      <form>
        <v-text-field
          v-model="name"
          :error-messages="nameErrors"
          :counter="10"
          label="Name"
          required
          @input="$v.name.$touch()"
          @blur="$v.name.$touch()"
        ></v-text-field>
        <v-text-field v-model="Price" label="Price" required></v-text-field>
        <v-text-field
          v-model="currency"
          label="Currency"
          required
        ></v-text-field>

        <v-text-field v-model="State" label="State" required></v-text-field>

        <v-btn class="mr-4" @click="submit">
          submit
        </v-btn>
        <v-btn @click="clear">
          clear
        </v-btn>
      </form>
    </v-dialog>
  </div>
</template>

<script>
import { mapActions } from "vuex";
export default {
  data: () => ({
    name: "",
    price: "",
    currency: "",
    state: ""
  }),

  methods: {
    ...mapActions(["saveCertificate"]),
    
    submit() {
      const json = JSON.stringify({
        name: this.name,
        state: this.state,
        currency: this.currency,
        price: this.price
      });

      this.saveCertificate(json);
    },
    clear() {
      this.$v.$reset();
      this.name = "";
      this.price = "";
      this.select = "";
      this.currency = ""
    }
  }
};
</script>
