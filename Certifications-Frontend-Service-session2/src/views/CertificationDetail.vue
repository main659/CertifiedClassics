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
        <v-btn
          id="cert-DeleteCertificationBtn"
          @click="deleteCert"
          color="error"
          dark
        >
          Delete
        </v-btn>
      </v-row>
      <v-row v-if="currentCertification">
        <v-col cols="8">
          <h1>{{ currentCertification.name }}</h1>
          <v-text-field v-if="edit" v-model="certificationURL"></v-text-field>
          <a v-else :href="currentCertification.url" target="_blank">
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
          <v-row>
            <v-card v-if="edit" class="mx-auto" max-width="500">
              <v-list shaped>
                <template v-for="(item, i) in allSkills">
                  <v-divider v-if="!item" :key="`divider-${i}`"></v-divider>
                  <v-list-item
                    v-else
                    :key="`item-${i}`"
                    :value="item.name"
                    active-class="deep-purple--text text--accent-4"
                  >
                    <template v-slot:default="{ active }">
                      <v-list-item-content>
                        <v-list-item-title
                          v-text="item.name"
                        ></v-list-item-title>
                      </v-list-item-content>
                      <v-list-item-action>
                        <v-checkbox
                          :input-value="active"
                          color="deep-purple accent-4"
                        ></v-checkbox>
                      </v-list-item-action>
                    </template>
                  </v-list-item>
                </template>
              </v-list>
            </v-card>
          </v-row>
          <v-row>
            <v-card v-if="!edit" class="mx-auto" max-width="400" tile>
              <v-list v-if="curSkills.length > 0">
                <v-subheader>Skills</v-subheader>
                <v-list-item v-for="(item, i) in curSkills" :key="i">
                  <v-list-item-content>
                    <v-list-item-title v-text="item.name"></v-list-item-title>
                    <v-list-item-subtitle
                      v-text="item.description"
                    ></v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </v-card>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import Vue from "vue";
import { mapGetters } from "vuex";
import axios from "axios";

export default Vue.extend({
  name: "CertificationDetail",
  data() {
    return {
      edit: false,
      currentCertification: {},
      certificationURL: null,
      certificationPrice: null,
      certificationDescription: null,
      allSkills: {},
      curSkills: {}
    };
  },
  computed: {
    ...mapGetters(["certifications"])
  },
  mounted() {
    this.getSkills();
    this.getCurSkills();
  },
  methods: {
    async getCurSkills() {
      const { data } = await axios.get(
        this.currentCertification._links.skills.href
      );
      console.log(data._embedded.skills);
      this.curSkills = data._embedded.skills;
    },
    async getSkills() {
      const { data } = await axios.get("http://localhost:8080/skills");
      console.log(data._embedded.skills);
      this.allSkills = data._embedded.skills;
    },
    deleteCert() {
      this.$store.dispatch("deleteCertification", {
        price: this.certificationPrice,
        url: this.certificationURL,
        id: this.currentCertification.id,
        currency: this.currentCertification.currency,
        name: this.currentCertification.name,
        skills: this.currentCertification.skills,
        state: this.currentCertification.state,
        vouchers: this.currentCertification.vouchers
      });
      this.editModeOff();
      this.$forceUpdate();
    },
    save() {
      this.$store.dispatch("updateCertification", {
        price: this.certificationPrice,
        url: this.certificationURL,
        id: this.currentCertification.id,
        currency: this.currentCertification.currency,
        name: this.currentCertification.name,
        skills: this.currentCertification.skills,
        state: this.currentCertification.state,
        vouchers: this.currentCertification.vouchers
      });
      this.editModeOff();
      this.$forceUpdate();
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
  created() {
    console.log(this.certifications);
    this.currentCertification = this.certifications.find(
      ({ id }) => id == this.$route.params.id
    );
  }
});
</script>
