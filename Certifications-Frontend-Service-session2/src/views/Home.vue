<template>
  <div class="home">
    <v-row>
      <v-col cols="12">
        <h1>Available Certifications</h1>
        <div v-if="certifications !== undefined && certifications.length > 0">
          <v-row>
            <router-link
              v-for="certification in certifications"
              :key="certification.id"
              :to="'/certificationDetail/' + certification.id"
              tag="div"
            >
              <v-col>
                <CertificationItem
                  style="cursor: pointer"
                  :certification="certification"
                />
              </v-col>
            </router-link>
          </v-row>
        </div>
        <div v-else>
          <h3>No Certifications found</h3>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import Vue from "vue";
import CertificationItem from "@/components/CertificationItem.vue";
import { mapGetters, mapMutations } from "vuex";
import axios from "axios";

export default Vue.extend({
  name: "Home",
  components: {
    CertificationItem
  },
  computed: {
    ...mapGetters(["certifications", "sessionid"])
  },
  async mounted() {
    const config = {
      headers: {
        "Content-Type": "application/json",
        "auth_token": localStorage.getItem("auth_token")
      }
    };

    const { data } = await axios.get(
      "http://localhost:8080/certifications",
      config
    );
    console.log(data._embedded.certifications);
    this.certificationsMutation(data._embedded.certifications);
  },
  methods: {
    ...mapMutations(["certificationsMutation"])
  }
});
</script>
