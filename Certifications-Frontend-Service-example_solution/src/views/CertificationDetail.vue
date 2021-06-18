<template>
  <div id="certificationDetail">
    <v-container fluid>
      <v-row style="padding: 12px">
        <div v-if="edit">
          <v-btn
            id="cert-SaveCertificationBtn"
            @click="save"
            color="primary"
            dark
          >
            Save
          </v-btn>
          <v-btn
            id="cert-CancelEditCertificationBtn"
            @click="editModeOff"
            color="primary"
            dark
          >
            Cancel
          </v-btn>
        </div>
        <v-btn
          v-else
          id="cert-EditCertificationBtn"
          @click="editModeOn"
          color="primary"
          dark
        >
          Edit
        </v-btn>
      </v-row>
      <v-row v-if="currentCertification">
        <v-col cols="8">
          <h1>{{ currentCertification.name }}</h1>
          <v-text-field v-if="edit" v-model="certificationURL"></v-text-field>
          <a v-else :href="currentCertification.url">
            {{ currentCertification.url }}</a
          >
          <v-row v-if="edit">
            <v-col cols="1"> Price: </v-col>
            <v-col cols="10">
              <v-text-field v-model="certificationPrice"></v-text-field>
            </v-col>
          </v-row>
          <p v-else>
            Price:
            <strong>{{ currentCertification.price }}</strong>
          </p>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "CertificationDetail",
  data() {
    return {
      edit: false,
      currentCertification: {},
      certificationURL: null,
      certificationPrice: null,
      certificationDescription: null
    };
  },
  created() {
    console.log(this.certifications);
    this.currentCertification = this.certifications.find(
      ({ id }) => id == this.$route.params.id
    );
  },
  methods: {
    save() {
      // to be implemented
    },
    editModeOn() {
      this.edit = true;
      this.certificationURL = this.currentCertification.url;
      this.certificationPrice = this.currentCertification.price;
      this.certificationDescription = this.currentCertification.description;
    },
    editModeOff() {
      this.edit = false;
      this.certificationURL = null;
      this.certificationPrice = null;
      this.certificationDescription = null;
    }
  },
  computed: {
    ...mapGetters(["certifications"])
  }
};
</script>

<style></style>
