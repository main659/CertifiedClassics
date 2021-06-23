<template>
  <div>
    <v-row>
      <v-col cols="12">
        <h1>Your profile</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <h3>Name: {{userInfo.name}} {{userInfo.surname}}</h3>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <h3>User e-mail: {{userInfo.email}}</h3>
      </v-col>
    </v-row>
    <v-divider />
    <v-row>
      <v-col cols="12" style="padding-top: 2em">
        <v-btn @click.stop="newCertificationDialog = true" dark color="primary"
          >New Ceritification Request</v-btn
        >
        <v-dialog v-model="newCertificationDialog" persistent max-width="600px">
          <v-card>
            <v-card-title>
              New Certification Request
            </v-card-title>
            <v-card-text>
              <v-form ref="newCertForm" v-model="valid">
                <v-row>
                  <v-col cols="12">
                    <v-text-field
                      label="Name"
                      v-model="certificationName"
                      :rules="nameRules"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="6">
                    <v-text-field
                      label="Price"
                      v-model="certificationPrice"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="6">
                    <v-text-field
                      label="Currency"
                      :rules="currencyRules"
                      v-model="certificationCurrency"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="12">
                    <v-text-field
                      label="URL"
                      :rules="urlRules"
                      v-model="certificationUrl"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer />
              <v-btn color="secondary" @click="closeNewCertificationDialog"
                >Close</v-btn
              >
              <v-btn
                :disabled="!valid"
                color="primary"
                @click="sendNewCertificationDialog"
                >Send</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios";

export default Vue.extend({
  data() {
    return {
      valid: true,
      newCertificationDialog: false,
      certificationName: "",
      certificationPrice: 0,
      certificationCurrency: "",
      certificationUrl: "",
      nameRules: [(v) => !!v || "Name is required"],
      currencyRules: [(v) => !!v || "Currency is required"],
      urlRules: [(v) => !!v || "URL is required"],
      userInfo: [],
    };
  },
  mounted() {
    this.getUsers();
  },
  methods: {
    async getUsers() {
      const config = {
        headers: {
          'Content-Type': 'application/json',
          "auth_token": localStorage.getItem("auth_token"),
        }
      };
      const { data } = await axios.get("http://localhost:8080/getuser", config);
      console.log(data);
      this.userInfo = data;
    },
    closeNewCertificationDialog() {
      this.newCertificationDialog = false;
      this.certificationName = "";
      this.certificationPrice = 0;
      this.certificationCurrency = "";
      this.certificationUrl = "";
    },
    sendNewCertificationDialog() {
      this.$store.dispatch("createCertificationRequest", {
        name: this.certificationName,
        currency: this.certificationCurrency,
        price: this.certificationPrice,
        url: this.certificationUrl,
      });
      this.closeNewCertificationDialog();
    },
  },
});
</script>
