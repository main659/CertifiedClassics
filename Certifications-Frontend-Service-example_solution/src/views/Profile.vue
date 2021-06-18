<template>
  <div id="profile">
    <v-row>
      <v-col cols="12">
        <h1>
          Your profile
        </h1>
      </v-col>
    </v-row>
    <v-divider />
    <v-row justify="center">
      <v-col cols="12">
        <v-card-actions class="justify-center">
          <v-btn
            @click.stop="newCertificationDialog = true"
            color="primary"
            dark
          >
            New Certification Request
          </v-btn>
        </v-card-actions>
        <v-dialog v-model="newCertificationDialog" persistent max-width="600px">
          <v-card>
            <v-card-title class="headline"
              >New Certification Request</v-card-title
            >
            <v-card-text>
              <v-form ref="newCertForm" lazy-validation>
                <v-row>
                  <v-col cols="12">
                    <v-text-field
                      label="Name"
                      v-model="certificationName"
                      :rules="nameRules"
                      required
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
                      v-model="certificationCurrency"
                      :rules="currencyRules"
                      required
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
              <v-spacer></v-spacer>

              <v-btn @click="closeNewCertificationDialog()" color="secondary">
                Close
              </v-btn>

              <v-btn
                :disabled="!valid"
                @click="acceptNewCertificationDialog()"
                color="primary"
              >
                Accept
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import Vue from "vue";
// import { mapGetters } from 'vuex'

export default Vue.extend({
  name: "Profile",
  head() {
    return {
      title: "Profile"
    };
  },
  data() {
    return {
      valid: true,
      newCertificationDialog: false,
      certificationName: "",
      certificationPrice: 0,
      certificationCurrency: "",
      certificationUrl: "",
      nameRules: [v => !!v || "Name is required"],
      currencyRules: [v => !!v || "Currency is required"],
      urlRules: [v => !!v || "URL is required"]
    };
  },
  mounted() {
    //
  },
  methods: {
    closeNewCertificationDialog() {
      this.newCertificationDialog = false;
      this.certificationName = "";
      this.certificationCurrency = "";
      this.certificationPrice = 0;
      this.certificationUrl = "";
      this.valid = true;
    },
    acceptNewCertificationDialog() {
      if (this.$refs.newCertForm.validate()) {
        this.valid = true;
      } else {
        this.valid = false;
      }
      if (this.valid) {
        this.$store.dispatch("createCertificationRequest", {
          name: this.certificationName,
          currency: this.certificationCurrency,
          price: this.certificationPrice,
          url: this.certificationUrl
        });
        this.closeNewCertificationDialog();
      }
    }
  }
});
</script>

<style lang="scss" scoped>
#profile {
  padding: 1em;
}

h1.notifications-title {
  float: left;
  margin-right: 16px;
}

div.v-input--switch--inset {
  margin-top: 8px !important;
}
.card-wrapper {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  height: 100%;
}
</style>
