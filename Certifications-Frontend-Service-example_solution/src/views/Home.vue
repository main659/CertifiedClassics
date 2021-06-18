<template>
  <div class="home">
    <v-row>
      <v-col cols="12">
        <h1 style="margin-left: 2rem">My Active Vouchers</h1>
        <div
          v-if="vouchers !== undefined && vouchers.length > 0"
          class="card-wrapper"
        >
          <v-row>
            <router-link
              v-for="voucher in vouchers"
              :key="voucher.id"
              :to="'/voucherDetail/' + voucher.id"
              v-bind:id="'certDashboardReceivedVoucher' + voucher.id"
              tag="div"
            >
              <v-col>
                <VoucherItem
                  style="cursor: pointer"
                  :key="voucher.id"
                  :voucher="voucher"
                />
              </v-col>
            </router-link>
          </v-row>
        </div>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <h1 style="margin-left: 2rem">Available certifications</h1>
        <div
          v-if="certifications !== undefined && certifications.length > 0"
          class="card-wrapper"
        >
          <v-row>
            <router-link
              v-for="certification in certifications"
              :key="certification.id"
              :to="'/certificationDetail/' + certification.id"
              v-bind:id="'certDashboardCertification' + certification.id"
              tag="div"
            >
              <v-col>
                <CertificationItem
                  style="cursor: pointer"
                  :key="certification.id"
                  :certification="certification"
                  :width="300"
                />
              </v-col>
            </router-link>
          </v-row>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import Vue from "vue";
import VoucherItem from "@/components/VoucherItem.vue";
import CertificationItem from "@/components/CertificationItem.vue";
import { mapGetters, mapMutations } from "vuex";
import axios from "axios";

export default Vue.extend({
  name: "Home",
  components: {
    VoucherItem,
    CertificationItem
  },
  computed: {
    ...mapGetters(["loggedIn", "vouchers", "certifications"])
  },
  async mounted() {
    if (this.loggedIn === false) {
      this.$router.push("/login");
    }
    const headers = {
      "Cache-Control": "no-cache",
      Pragma: "no-cache",
      Expires: "0"
    };
    // /assets/voucherMockData.json
    const { data } = await axios.get("http://localhost:8080/vouchers", {
      headers: headers
    });
    console.log("Vouchers:");
    console.log(data._embedded.vouchers);
    this.vouchersMutation(data._embedded.vouchers);

    // /assets/certificationMockData.json
    await axios
      .get("http://localhost:8080/certifications", {
        headers: headers
      })
      .then(response => {
        console.log("Certifications:");
        console.log(response.data._embedded.certifications);
        this.certificationsMutation(response.data._embedded.certifications);
      });
  },
  methods: {
    ...mapMutations(["vouchersMutation", "certificationsMutation"])
  }
});
</script>
