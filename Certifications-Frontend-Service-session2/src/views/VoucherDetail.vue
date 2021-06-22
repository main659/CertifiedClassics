<template>
  <v-row justify="center">
    <v-col cols="4" class="justify-center">
      <v-card>
        <v-card-title>
          <v-col cols="6">
            {{ currentVoucher.voucherCode }}
          </v-col>
          <v-col cols="6">
            <v-btn v-if="currentVoucher.state == 'NEW'" color="primary">{{
              currentVoucher.state
            }}</v-btn>
            <v-btn v-if="currentVoucher.state == 'ACTIVE'" color="secondary">{{
              currentVoucher.state
            }}</v-btn>
            <v-btn v-if="currentVoucher.state == 'PROPOSED'" color="danger">{{
              currentVoucher.state
            }}</v-btn>
          </v-col>
        </v-card-title>
        <v-card-text>
          Valid until: {{ currentVoucher.validUntil }}
          <v-col>
            <h3>Certification:</h3>
            {{ currentVoucherCertification.name }}
          </v-col>
        </v-card-text>

        <v-footer>
          <v-col cols="12">
            <v-icon>mdi-account</v-icon>
            <v-select
              :items="users"
              label="User*"
              required
              :item-text="item => `${item.name} ${item.surname}`"
              item-value="id"
              :value="userid"
              v-model="userid"
            >
            </v-select>
          </v-col>
        </v-footer>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">
            Close
          </v-btn>
          <v-btn color="blue darken-1" text @click="sendUpdateVoucherDialog">
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";
export default {
  name: "VoucherDetail",
  data() {
    return {
      value: false,
      currentVoucher: {},
      currentVoucherUser: {},
      currentVoucherCertification: {},
      user: {},
      userid: "",
      certificationid: ""
    };
  },
  methods: {
    sendUpdateVoucherDialog() {
      if (this.userid != null) {
        this.$store.dispatch("createUpdateVoucherRequest", {
          user: {
            // eslint-disable-next-line @typescript-eslint/camelcase
            id: this.userid
          }
        });
        this.dialog = false;
      } else {
        this.$store.dispatch("createUpdateVoucherRequest", {
          //eslint-disable-next-line @typescript-eslint/camelcase
          certification: {
            id: this.certificationid
          },
          state: this.states,
          validUntil: this.date,
          voucherCode: this.vouchercode
        });
        this.dialog = false;
      }
    },
    async getUser() {
      axios
        .get(
          "http://localhost:8080/vouchers/" + this.$route.params.id + "/user"
        )
        .then(response => {
          this.currentVoucherUser = response.data;
          this.userid = this.currentVoucherUser.id;
          console.log("currentVOucherUser", this.currentVoucherUser);
        })
        .catch(error => {
          if (error.response && error.response.status === 404) {
            console.clear();
            console.log("empty");
          }
        });
      axios
        .get(
          "http://localhost:8080/vouchers/" +
            this.$route.params.id +
            "/certification"
        )
        .then(response => {
          this.currentVoucherCertification = response.data;
          this.certificationid = this.currentVoucherCertification.id;
          console.log(
            "currentVoucherCertification",
            this.currentVoucherCertification
          );
        })
        .catch(error => {
          if (error.response && error.response.status === 404) {
            console.clear();
            console.log("error", error);
          }
        });
    }
  },
  created() {
    this.currentVoucher = this.vouchers.find(
      ({ id }) => id == this.$route.params.id
    );
    this.getUser(), console.log(this.vouchers);
    console.log("currentVoucher", this.currentVoucher);
    // console.log("current user ", this.currentVoucherUser);
  },
  computed: {
    ...mapGetters(["vouchers"]),
    ...mapGetters(["users"]),
    ...mapGetters(["certifications"])
  }
};
</script>
