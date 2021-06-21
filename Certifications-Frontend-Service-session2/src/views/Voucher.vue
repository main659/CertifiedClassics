<template>
  <div class="home">
    <h1>Available vouchers</h1>
    <br />
    <v-row justify="center">
      <v-dialog v-model="dialog" persistent max-width="600px">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="primary"
            @click.stop="dialog = true"
            dark
            v-bind="attrs"
            v-on="on"
          >
            Add new
          </v-btn>
        </template>
        <v-card>
          <v-form ref="newVoucherForm" v-model="valid">
            <v-card-title>
              <span class="text-h5">Create Voucher</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12">
                    <v-text-field
                      label="Voucher Code*"
                      type="text"
                      required
                      :rules="voucherCodeRules"
                      v-model="vouchercode"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" sm="12">
                    <v-select
                      :items="certifications"
                      :item-text="item => `${item.name}`"
                      item-value="id"
                      label="Certification"
                      :rules="certificationRules"
                      v-model="certificationid"
                    ></v-select>
                  </v-col>
                  <v-col cols="12" sm="12" md="12">
                    <v-menu
                      ref="menu"
                      v-model="menu"
                      :close-on-content-click="false"
                      :return-value.sync="date"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          v-model="date"
                          label="Valid until"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker v-model="date" no-title scrollable>
                        <v-spacer></v-spacer>
                        <v-btn text color="primary" @click="menu = false">
                          Cancel
                        </v-btn>
                        <v-btn
                          text
                          color="primary"
                          @click="$refs.menu.save(date)"
                        >
                          OK
                        </v-btn>
                      </v-date-picker>
                    </v-menu>
                  </v-col>
                  <v-col cols="12" sm="6">
                    <v-select
                      :items="['NEW', 'ACTIVE', 'PROPOSED']"
                      label="State*"
                      required
                      :rules="stateRules"
                      v-model="states"
                    ></v-select>
                  </v-col>
                  <v-col cols="12" sm="6">
                    <v-select
                      :items="users"
                      label="User*"
                      required
                      :item-text="item => `${item.name} ${item.surname}`"
                      item-value="id"
                      v-model="userid"
                    ></v-select>
                  </v-col>
                </v-row>
              </v-container>
              <small>*indicates required field</small>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="dialog = false">
                Close
              </v-btn>
              <v-btn
                color="blue darken-1"
                :disabled="!valid"
                text
                @click="sendNewVoucherDialog"
              >
                Save
              </v-btn>
            </v-card-actions>
          </v-form>
        </v-card>
      </v-dialog>
    </v-row>
    <v-row>
      <v-col cols="12">
        <div v-if="vouchers !== undefined && vouchers.length > 0">
          <v-row>
            <router-link
              v-for="voucher in vouchers"
              :key="voucher.id"
              :to="'/voucherDetail/' + voucher.id"
              tag="div"
            >
              <v-col>
                <VoucherDetail style="cursor: pointer" :voucher="voucher" />
              </v-col>
            </router-link>
          </v-row>
        </div>
        <div v-else>
          <h3>No Voucher found</h3>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import Vue from "vue";
import { mapGetters, mapMutations } from "vuex";
import axios from "axios";
import VoucherDetail from "../components/VoucherComponent.vue";

export default Vue.extend({
  name: "Voucher",
  components: {
    VoucherDetail
  },
  computed: {
    ...mapGetters(["vouchers"]),
    ...mapGetters(["users"]),
    ...mapGetters(["certifications"])
  },
  mounted() {
    this.getVouchers();
    this.getUsers();
    this.getCertification();
  },
  methods: {
    ...mapMutations(["usersMutation"]),
    ...mapMutations(["vouchersMutation"]),
    ...mapMutations(["certificationsMutation"]),
    async getVouchers() {
      const { data } = await axios.get("http://localhost:8080/vouchers");
      console.log(data._embedded.vouchers);
      this.vouchersMutation(data._embedded.vouchers);
    },
    async getUsers() {
      const { data } = await axios.get("http://localhost:8080/users");
      console.log(data._embedded.users);
      this.usersMutation(data._embedded.users);
    },
    async getCertification() {
      const { data } = await axios.get("http://localhost:8080/certifications");
      console.log(data._embedded.certifications);
      this.certificationsMutation(data._embedded.certifications);
    },
    sendNewVoucherDialog() {
      if (this.userid != null) {
        this.$store.dispatch("createVoucherRequest", {
          //eslint-disable-next-line @typescript-eslint/camelcase
          certification: {
            id: this.certificationid
          },
          state: this.states,
          user: {
            // eslint-disable-next-line @typescript-eslint/camelcase
            id: this.userid
          },
          validUntil: "2021-06-21",
          voucherCode: this.vouchercode
        });
        this.dialog = false;
      } else {
        this.$store.dispatch("createVoucherRequest", {
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
    }
  },
  data() {
    return {
      valid: true,
      dialog: false,
      certificationid: "",
      states: "",
      userid: null,
      validuntil: "",
      vouchercode: "",
      date: new Date().toISOString().substr(0, 10),
      menu: false,
      voucherCodeRules: [v => !!v || "Voucher code is required"],
      stateRules: [v => !!v || "State is required"],
      certificationRules: [v => !!v || "Certification is required"]
    };
  }
});
</script>
